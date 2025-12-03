package com.thecode.WebSite_CHECK_XML.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;
import com.thecode.WebSite_CHECK_XML.Model.application.DichVuKyThuat;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBGroup;
import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML2;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Check_Error_KCB {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    // ----bỏ dấu cách đầu và cuối của dữ liệu
    private static String norm(String s) {
        return s == null ? null : s.trim();
    }

    private static BacSi findBacSiById(List<BacSi> ds, String id) {
        if (id == null) return null;
        String nid = norm(id);
        for (BacSi b : ds) {
            String bid = norm(b.getMaBS());
            if (bid != null && bid.equals(nid)) return b;
        }
        return null;
    }


// =============================================
// 🔍 PHÁT HIỆN BÁC SĨ CHỈ ĐỊNH / THỰC HIỆN TRÙNG GIỜ
//    (NÂNG CẤP: dùng TimeBlock + kiểm tra KQ để tránh THYL bắt trước KQ của hồ sơ khác)
// =============================================

private static final Map<String, Map<String, Set<String>>> GLOBAL_YL_MAP = new HashMap<>();
private static final Map<String, Map<String, Set<String>>> GLOBAL_THYL_MAP = new HashMap<>();

// MỚI: lưu map mã LK -> danh sách XML3 (dùng để tìm ngayKq cho mỗi hồ sơ)
private static final Map<String, List<XML3>> GLOBAL_LK_TO_CLS = new HashMap<>();

// TimeBlock helper
private static class TimeBlock {
    LocalDateTime start; // THYL (thời điểm bắt đầu thực hiện)
    LocalDateTime end;   // KQ (thời điểm kết thúc)
    String maLK;

    TimeBlock(LocalDateTime s, LocalDateTime e, String lk) {
        this.start = s;
        this.end = e;
        this.maLK = lk;
    }
}

// Kiểm tra overlap (bao gồm tiếp xúc). Trả true nếu [st,en] đụng bất kỳ block nào
private static boolean isOverlap(LocalDateTime st, LocalDateTime en, List<TimeBlock> blocks) {
    if (blocks == null) return false;
    for (TimeBlock b : blocks) {
        // Nếu không (en < b.start || st > b.end) ==> overlap
        if (!(en.isBefore(b.start) || st.isAfter(b.end))) {
            return true;
        }
    }
    return false;
}

// Kiểm tra 1 thời điểm có nằm trong bất kỳ block không (dùng cho kiểm tra YL tránh đặt YL vào giữa block)
private static boolean isInsideBlocks(LocalDateTime t, List<TimeBlock> blocks) {
    if (blocks == null) return false;
    for (TimeBlock b : blocks) {
        if (!t.isBefore(b.start) && !t.isAfter(b.end)) return true;
    }
    return false;
}

// Lấy ngayKq của hồ sơ (ưu tiên xml3 có ngayThYl == referenceTime, hoặc xml3 có ngayYl == referenceTime).
// Nếu không tìm được, trả về ngày KQ muộn nhất trong hồ sơ (nếu có), ngược lại trả null.
private static String getKQOfHoSo(String maLK, String referenceTime) {
    List<XML3> list = GLOBAL_LK_TO_CLS.get(maLK);
    if (list == null || list.isEmpty()) return null;

    // 1) tìm xml3 có ngayThYl == referenceTime và có ngayKq
    if (referenceTime != null) {
        for (XML3 x : list) {
            if (x == null) continue;
            if (referenceTime.equals(x.getNgayThYl()) && x.getNgayKq() != null && x.getNgayKq().matches("\\d{12}")) {
                return x.getNgayKq();
            }
            if (referenceTime.equals(x.getNgayYl()) && x.getNgayKq() != null && x.getNgayKq().matches("\\d{12}")) {
                return x.getNgayKq();
            }
        }
    }

    // 2) nếu không có match trên referenceTime, trả về KQ lớn nhất (muộn nhất) nếu có
    String latest = null;
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    for (XML3 x : list) {
        try {
            String kq = x.getNgayKq();
            if (kq != null && kq.matches("\\d{12}")) {
                if (latest == null) latest = kq;
                else {
                    LocalDateTime a = LocalDateTime.parse(latest, fmt);
                    LocalDateTime b = LocalDateTime.parse(kq, fmt);
                    if (b.isAfter(a)) latest = kq;
                }
            }
        } catch (Exception e) {
            // bỏ qua parse lỗi
        }
    }
    return latest;
}

private static void checkBacSiChiDinhTrungGio(HoSoYTe hs, ErrorKCBGroup group) {
    if (hs == null || hs.getDsCLS() == null || hs.getDsCLS().isEmpty()) return;

    List<XML3> dsCLS = hs.getDsCLS();
    String maLK = norm(hs.getMaLk());
    final String BS_BO_QUA = "008003/BD-CCHN";

    // MỚI: lưu map mã LK -> dsCLS (ghi đè hoặc thêm, dùng khi build timeline)
    GLOBAL_LK_TO_CLS.put(maLK, dsCLS);

    for (XML3 xml3 : dsCLS) {
        if (xml3 == null) continue;

        String bsCD = norm(xml3.getMaBacSi());
        String gioYL = norm(xml3.getNgayYl());
        String bsTH = norm(xml3.getNguoiThucHien());
        String gioTHYL = norm(xml3.getNgayThYl());

        if (BS_BO_QUA.equalsIgnoreCase(bsCD) || BS_BO_QUA.equalsIgnoreCase(bsTH)) continue;

        if (bsCD != null && gioYL != null && gioYL.matches("\\d{12}")) {
            GLOBAL_YL_MAP.computeIfAbsent(bsCD, k -> new HashMap<>())
                    .computeIfAbsent(gioYL, k -> new HashSet<>()).add(maLK);
        }

        if (bsTH != null && gioTHYL != null && gioTHYL.matches("\\d{12}")) {
            GLOBAL_THYL_MAP.computeIfAbsent(bsTH, k -> new HashMap<>())
                    .computeIfAbsent(gioTHYL, k -> new HashSet<>()).add(maLK);
        }
    }

    // 🔹 In báo cáo trùng giờ (phiên bản mới, an toàn với KQ)
    printGoiYXepGio();
}

private static void printGoiYXepGio() {
    final String BS_BO_QUA = "008003/BD-CCHN";
    int stepMinutes = 15;
    DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    // ==========================
    // 🚑 XÂY DỰNG TIMELINE CHO MỖI BÁC SĨ (dựa trên THYL -> KQ)
    // ==========================
    Map<String, List<TimeBlock>> bsTimeline = new HashMap<>();

    // Duyệt tất cả entry trong GLOBAL_THYL_MAP để build block
    for (Map.Entry<String, Map<String, Set<String>>> entryBS : GLOBAL_THYL_MAP.entrySet()) {
        String bs = entryBS.getKey();
        if (BS_BO_QUA.equalsIgnoreCase(bs)) continue;

        List<TimeBlock> blocks = new ArrayList<>();
        Map<String, Set<String>> thMap = entryBS.getValue();

        for (Map.Entry<String, Set<String>> thEntry : thMap.entrySet()) {
            String thTime = thEntry.getKey(); // "yyyyMMddHHmm"
            for (String lk : thEntry.getValue()) {
                try {
                    LocalDateTime st = LocalDateTime.parse(thTime, inputFmt);

                    // tìm KQ tương ứng cho hồ sơ lk (nếu có)
                    String kq = getKQOfHoSo(lk, thTime);
                    LocalDateTime en;
                    if (kq != null && kq.matches("\\d{12}")) {
                        en = LocalDateTime.parse(kq, inputFmt);
                    } else {
                        // Nếu không có KQ, giả định kết thúc sau 10 phút (có thể chỉnh)
                        en = st.plusMinutes(10);
                    }

                    // Nếu end trước start vì dữ liệu sai, đặt end = start + 1 phút
                    if (en.isBefore(st)) en = st.plusMinutes(1);

                    blocks.add(new TimeBlock(st, en, lk));
                } catch (Exception e) {
                    // bỏ qua parse lỗi
                }
            }
        }

        // sort blocks theo start
        blocks.sort(Comparator.comparing(b -> b.start));
        bsTimeline.put(bs, blocks);
    }

    // ==========================
    // 🚑 XỬ LÝ TRÙNG GIỜ YL (BÁC SĨ CHỈ ĐỊNH)
    // ==========================
    System.out.println("\n===== GỢI Ý DÀN XẾP GIỜ YL =====");

    for (Map.Entry<String, Map<String, Set<String>>> entryBS : GLOBAL_YL_MAP.entrySet()) {
        String bs = entryBS.getKey();
        if (BS_BO_QUA.equalsIgnoreCase(bs)) continue;

        Map<String, Set<String>> gioMap = entryBS.getValue();

        // lấy timeline của BS nếu có (dùng để tránh đặt YL vào thời gian BS đang bận)
        List<TimeBlock> blocks = bsTimeline.getOrDefault(bs, new ArrayList<>());

        // Gom tất cả giờ YL hiện có để tránh trùng YL giữa các hồ sơ
        Set<String> allExistingTimes = new HashSet<>();
        entryBS.getValue().forEach((t, s) -> allExistingTimes.add(t));

        for (Map.Entry<String, Set<String>> gioEntry : gioMap.entrySet()) {
            String gio = gioEntry.getKey();
            Set<String> lkSet = gioEntry.getValue();

            if (lkSet.size() <= 1) continue;

            List<String> lkList = new ArrayList<>(lkSet);
            System.out.println("BS chỉ định " + bs + " - giờ YL " + gio + " bị trùng: " + String.join(", ", lkList));

            LocalDateTime baseTime = null;
            try {
                baseTime = LocalDateTime.parse(gio, inputFmt);
            } catch (Exception e) {
                continue;
            }

            // usedTimes: giờ YL đã đề xuất trong nhóm này (để tránh lặp lại)
            Set<String> usedTimes = new HashSet<>(lkSet);

            for (int i = 1; i < lkList.size(); i++) {
                LocalDateTime candidate = baseTime.plusMinutes(stepMinutes);
                String candidateStr = candidate.format(inputFmt);

                // tránh trùng YL nội bộ và tránh rơi vào bất kỳ TimeBlock (BS bận) nào
                while (allExistingTimes.contains(candidateStr) || usedTimes.contains(candidateStr) || isInsideBlocks(candidate, blocks)) {
                    candidate = candidate.plusMinutes(stepMinutes);
                    candidateStr = candidate.format(inputFmt);
                }

                usedTimes.add(candidateStr);
                allExistingTimes.add(candidateStr);

                System.out.println("   → Hồ sơ " + lkList.get(i) + " gợi ý YL sửa thành: "
                        + candidateStr + " (" + candidate.format(displayFmt) + ")");
            }
        }
    }

    // ==========================
    // 🚑 XỬ LÝ TRÙNG GIỜ THYL (BÁC SĨ THỰC HIỆN) VỚI KIỂM TRA KQ (TIMELINE)
    // ==========================
    System.out.println("\n===== GỢI Ý DÀN XẾP GIỜ THYL =====");

    for (Map.Entry<String, Map<String, Set<String>>> entryBS : GLOBAL_THYL_MAP.entrySet()) {
        String bs = entryBS.getKey();
        if (BS_BO_QUA.equalsIgnoreCase(bs)) continue;

        Map<String, Set<String>> gioMap = entryBS.getValue();

        // timeline hiện tại của BS (các block THYL->KQ đã có)
        List<TimeBlock> blocks = bsTimeline.getOrDefault(bs, new ArrayList<>());

        for (Map.Entry<String, Set<String>> gioEntry : gioMap.entrySet()) {
            String gio = gioEntry.getKey();
            Set<String> lkSet = gioEntry.getValue();

            if (lkSet.size() <= 1) continue;

            List<String> lkList = new ArrayList<>(lkSet);
            System.out.println("BS thực hiện " + bs + " - giờ THYL " + gio + " bị trùng: " + String.join(", ", lkList));

            LocalDateTime baseTime;
            try {
                baseTime = LocalDateTime.parse(gio, inputFmt);
            } catch (Exception e) {
                continue;
            }

            // Lưu các block hiện có vào một danh sách động để thêm các gợi ý mới (tránh đụng sau này)
            List<TimeBlock> dynamicBlocks = new ArrayList<>(blocks);

            // (quan trọng) đánh dấu các hồ sơ đang trùng này dưới dạng block hiện thời để tránh gợi ý đè lên nhau
            for (String lk : lkList) {
                String kq = getKQOfHoSo(lk, gio);
                try {
                    LocalDateTime st = LocalDateTime.parse(gio, inputFmt);
                    LocalDateTime en = (kq != null && kq.matches("\\d{12}")) ? LocalDateTime.parse(kq, inputFmt) : st.plusMinutes(10);
                    if (en.isBefore(st)) en = st.plusMinutes(1);
                    dynamicBlocks.add(new TimeBlock(st, en, lk));
                } catch (Exception e) {
                    // bỏ qua parse
                }
            }
            // sort lại
            dynamicBlocks.sort(Comparator.comparing(b -> b.start));

            // Bắt đầu gợi ý từ hồ sơ thứ 2, 3...
            for (int i = 1; i < lkList.size(); i++) {
                // candidate bắt đầu từ baseTime + stepMinutes, sau đó tăng dần đến khi không overlap
                LocalDateTime candidate = baseTime.plusMinutes(stepMinutes);
                LocalDateTime candidateEnd = candidate.plusMinutes(10); // giả định 10 phút cho DV nếu không biết duration chính xác

                // nếu candidate nằm trong bất kỳ block nào -> dịch sang +stepMinutes cho đến khi free
                while (isOverlap(candidate, candidateEnd, dynamicBlocks)) {
                    candidate = candidate.plusMinutes(stepMinutes);
                    candidateEnd = candidate.plusMinutes(stepMinutes);
                }

                // đảm bảo candidate bắt đầu sau tất cả block có end <= candidate (đã được isOverlap kiểm)
                String newTHYL = candidate.format(inputFmt);
                String newTHYLDisplay = candidate.format(displayFmt);

                System.out.println("   → Hồ sơ " + lkList.get(i) + " gợi ý THYL = " + newTHYL + " (" + newTHYLDisplay + ")");

                // thêm block mới vào dynamicBlocks để tránh đè cho các hồ sơ tiếp theo
                dynamicBlocks.add(new TimeBlock(candidate, candidateEnd, lkList.get(i)));
                dynamicBlocks.sort(Comparator.comparing(b -> b.start));
            }
        }
    }
}





    private static void checkThuocSauKQ(HoSoYTe hs, ErrorKCBGroup group) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    // Tìm thời gian KQ lớn nhất của hồ sơ (dịch vụ kỹ thuật cuối cùng)
    Optional<LocalDateTime> maxKQ = hs.getDsCLS().stream()
            .filter(x -> x.getNgayKq() != null)
            .map(x -> LocalDateTime.parse(x.getNgayKq(), fmt))
            .max(LocalDateTime::compareTo);

    if (maxKQ.isEmpty()) return; // không có KQ thì bỏ qua

    for (XML2 thuoc : hs.getDsThuoc()) {
        try {
            if (thuoc.getNgayYl() == null) continue;

            LocalDateTime timeThuoc = LocalDateTime.parse(thuoc.getNgayYl(), fmt);
            if (timeThuoc.isBefore(maxKQ.get())) {
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(hs.getMaLk());
                detail.setMaBn(hs.getMaBN());
                detail.setMaDichVu(thuoc.getMaThuoc());
                detail.setMaBsCĐ(thuoc.getMaBacSi());
                detail.setMaBsTH(thuoc.getMaBacSi());
                detail.setTenDichVu(thuoc.getTenThuoc());
                detail.setNgayYL(thuoc.getNgayYl());

                String thuocTimeStr = timeThuoc.format(displayFmt);
                String kqTimeStr = maxKQ.get().format(displayFmt);

                detail.setErrorDetail(
                    "⛔ Thời gian kê thuốc (" + thuocTimeStr +
                    ") phải sau khi có kết quả DVKT (" + kqTimeStr + ")"
                );
                group.addError(detail);
            }
        } catch (Exception e) {
            // Ghi log nếu cần, ví dụ: e.printStackTrace();
        }
    }
}



    // ------------------- NEW: hàm kiểm tra thời gian -------------------
   
   public static void checkThoiGianDongBo(List<XML3> dsCLS, String maLK, ErrorKCBGroup group) {
    if (dsCLS == null || dsCLS.isEmpty()) return;

    Set<String> skipSet = Set.of("02.03", "03.18", "10.19");

    List<XML3> validCLS = dsCLS.stream()
            .filter(xml3 -> !skipSet.contains(norm(xml3.getMaDichVu())))
            .collect(Collectors.toList());

    if (validCLS.isEmpty()) return;

    String firstNgayYL = validCLS.get(0).getNgayYl();
    String firstThYl = validCLS.get(0).getNgayThYl();

    for (XML3 xml3 : validCLS) {
        // check NgayYL
        if (xml3.getNgayYl() != null && !xml3.getNgayYl().equals(firstNgayYL)) {
            ErrorKCBDetail detail = new ErrorKCBDetail();
            detail.setMaLk(maLK);
            detail.setMaBsCĐ(xml3.getMaBacSi());
            detail.setTenDichVu(xml3.getTenDichVu());
            detail.setMaBsTH(xml3.getNguoiThucHien());
            detail.setMaDichVu(xml3.getMaDichVu());
            detail.setNgayYL(xml3.getNgayYl());
            detail.setNgayTHYL(xml3.getNgayThYl());
            detail.setNgaykq(xml3.getNgayKq());
            detail.setErrorDetail("Thời gian YL không đồng bộ trong hồ sơ");
            group.addError(detail);
        }

        // check NgayTHYL
        if (xml3.getNgayThYl() != null && !xml3.getNgayThYl().equals(firstThYl)) {
            ErrorKCBDetail detail = new ErrorKCBDetail();
            detail.setMaLk(maLK);
            detail.setMaDichVu(xml3.getMaDichVu());
            detail.setTenDichVu(xml3.getTenDichVu());
            detail.setMaBsCĐ(xml3.getMaBacSi());
            detail.setMaBsTH(xml3.getNguoiThucHien());
            detail.setNgayYL(xml3.getNgayYl());
            detail.setNgayTHYL(xml3.getNgayThYl());
            detail.setNgaykq(xml3.getNgayKq());
            detail.setErrorDetail("Thời gian THYL không đồng bộ trong hồ sơ");
            group.addError(detail);
        }
    }
}

/*private static void checkThoiGian(XML3 xml3, DichVuKyThuat allowed, String maLK, ErrorKCBGroup group) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    try {
        String maDv = xml3.getMaDichVu();
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // ✅ Nếu mã DV là 02.03 hoặc 03.18 → dùng ngày YL và KQ
        if ("02.03".equals(maDv) || "03.18".equals(maDv) || "10.19".equals(maDv) ) {
            if (xml3.getNgayYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayYl(), fmt);
                endTime = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }
        } 
        // ✅ Ngược lại → dùng ngày thực hiện (THYL) và KQ
        else {
            if (xml3.getNgayThYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayThYl(), fmt);
                endTime = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }
        }

        // ✅ 2. Kiểm tra: ngày yêu cầu (YL) < ngày thực hiện (THYL)
        if (xml3.getNgayYl() != null && xml3.getNgayThYl() != null) {
            LocalDateTime ngayYl = LocalDateTime.parse(xml3.getNgayYl(), fmt);
            LocalDateTime ngayThyl = LocalDateTime.parse(xml3.getNgayThYl(), fmt);

            if (!ngayYl.isBefore(ngayThyl)) { // tức là YL >= THYL
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaDichVu(maDv);
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());
                detail.setErrorDetail("Ngày yêu cầu (" + xml3.getNgayYl() + ") phải trước ngày thực hiện (" + xml3.getNgayThYl() + ")");
                group.addError(detail);
            }
        }

        // ⚙️ Nếu có đủ dữ liệu thời gian thì kiểm tra
        if (startTime != null && endTime != null) {
            long diffMinutes = Duration.between(startTime, endTime).toMinutes();

            if (diffMinutes < allowed.getThoiGianToiThieu() || diffMinutes > allowed.getThoiGianToiDa()) {
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaDichVu(maDv);
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());
                detail.setMaBsCĐ(xml3.getMaBacSi());
                detail.setMaBsTH(xml3.getNguoiThucHien());

                detail.setErrorDetail("Thời gian DV " + allowed.getTenDV()
                        + " lệch " + diffMinutes + "p, chuẩn " 
                        + allowed.getThoiGianToiThieu() + "-" + allowed.getThoiGianToiDa());

                group.addError(detail);
            }
        }

    } catch (Exception e) {
        // Có thể log để theo dõi dữ liệu lỗi
        // System.err.println("Lỗi xử lý thời gian DV " + xml3.getMaDichVu() + ": " + e.getMessage());
    }
}*/



/*private static void checkThoiGian(XML3 xml3, DichVuKyThuat allowed, String maLK, ErrorKCBGroup group) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    try {
        String maDv = xml3.getMaDichVu();

        boolean laCongKham =
            "02.03".equals(maDv) ||
            "03.18".equals(maDv) ||
            "10.19".equals(maDv);

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // 🔥 1. Công khám → dùng YL → KQ
        if (laCongKham) {
            if (xml3.getNgayYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayYl(), fmt);
                endTime = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }
        }
        // 🔥 2. DV kỹ thuật → dùng THYL → KQ
        else {
            if (xml3.getNgayThYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayThYl(), fmt);
                endTime = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }

            // ❗ Chỉ DV kỹ thuật mới phải check YL < THYL
            if (xml3.getNgayYl() != null && xml3.getNgayThYl() != null) {
                LocalDateTime ngayYl = LocalDateTime.parse(xml3.getNgayYl(), fmt);
                LocalDateTime ngayThyl = LocalDateTime.parse(xml3.getNgayThYl(), fmt);

                if (!ngayYl.isBefore(ngayThyl)) {
                    ErrorKCBDetail detail = new ErrorKCBDetail();
                    detail.setMaLk(maLK);
                    detail.setMaDichVu(maDv);
                    detail.setTenDichVu(xml3.getTenDichVu());
                    detail.setErrorDetail("Ngày yêu cầu (" + xml3.getNgayYl() + ") phải < ngày thực hiện (" + xml3.getNgayThYl() + ")");
                    group.addError(detail);
                }
            }
        }

        // 🔥 3. Kiểm tra khoảng thời gian thực tế
        if (startTime != null && endTime != null) {

            long diffMinutes = Duration.between(startTime, endTime).toMinutes();

            if (diffMinutes < allowed.getThoiGianToiThieu() ||
                diffMinutes > allowed.getThoiGianToiDa()) {

                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaDichVu(maDv);
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setErrorDetail(
                    "Thời gian DV " + allowed.getTenDV() +
                    " lệch " + diffMinutes + "p, chuẩn " +
                    allowed.getThoiGianToiThieu() + "-" + allowed.getThoiGianToiDa()
                );

                group.addError(detail);
            }
        }

    } catch (Exception e) {
        // ignore
    }
}*/


private static void checkThoiGian(
        XML3 xml3,
        DichVuKyThuat allowed,
        String maLK,
        ErrorKCBGroup group,
        LocalDateTime congKhamEndTime   // 🔥 thêm KQ công khám
) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    try {
        String maDv = xml3.getMaDichVu();

        boolean laCongKham =
            "02.03".equals(maDv) ||
            "03.18".equals(maDv) ||
            "10.19".equals(maDv);

        LocalDateTime startTime = null;
        LocalDateTime endTime = null;

        // 🔥 1. Công khám → YL → KQ
        if (laCongKham) {
            if (xml3.getNgayYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayYl(), fmt);
                endTime   = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }
        }
        // 🔥 2. DVKT → THYL → KQ
        else {
            if (xml3.getNgayThYl() != null && xml3.getNgayKq() != null) {
                startTime = LocalDateTime.parse(xml3.getNgayThYl(), fmt);
                endTime   = LocalDateTime.parse(xml3.getNgayKq(), fmt);
            }

            // 🔥 Rule cũ: YL < THYL
            if (xml3.getNgayYl() != null && xml3.getNgayThYl() != null) {
                LocalDateTime yl   = LocalDateTime.parse(xml3.getNgayYl(), fmt);
                LocalDateTime thyl = LocalDateTime.parse(xml3.getNgayThYl(), fmt);

                if (!yl.isBefore(thyl)) {
                    ErrorKCBDetail detail = new ErrorKCBDetail();
                    detail.setMaLk(maLK);
                    detail.setMaDichVu(maDv);
                    detail.setTenDichVu(xml3.getTenDichVu());
                    detail.setErrorDetail(
                        "Ngày YL (" + xml3.getNgayYl() + 
                        ") phải < ngày thực hiện (" + xml3.getNgayThYl() + ")"
                    );
                    group.addError(detail);
                }
            }

            // 🔥🔥🔥 RULE MỚI: YL_CLS > KQ Công khám
            if (congKhamEndTime != null && xml3.getNgayYl() != null) {
                LocalDateTime ylCls = LocalDateTime.parse(xml3.getNgayYl(), fmt);

                if (!ylCls.isAfter(congKhamEndTime)) {
                    ErrorKCBDetail detail = new ErrorKCBDetail();
                    detail.setMaLk(maLK);
                    detail.setMaDichVu(maDv);
                    detail.setTenDichVu(xml3.getTenDichVu());
                    detail.setErrorDetail(
                        "Ngày YL (" + xml3.getNgayYl() + 
                        ") của DV " + xml3.getTenDichVu() +
                        " phải > thời gian kết thúc công khám (" +
                        congKhamEndTime.format(fmt) + ")"
                    );
                    group.addError(detail);
                }
            }
        }

        // 🔥 3. Kiểm tra thời lượng DVKT
        if (startTime != null && endTime != null) {

            long diffMinutes = Duration.between(startTime, endTime).toMinutes();

            if (diffMinutes < allowed.getThoiGianToiThieu() ||
                diffMinutes > allowed.getThoiGianToiDa()) {

                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaDichVu(maDv);
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setErrorDetail(
                    "Thời gian DV " + allowed.getTenDV() +
                    " lệch " + diffMinutes + "p, chuẩn " +
                    allowed.getThoiGianToiThieu() + "-" + allowed.getThoiGianToiDa()
                );

                group.addError(detail);
            }
        }

    } catch (Exception e) {
        // ignore nhưng KHÔNG NÊN, nên log ra
    }
}




    
    private static void checkGioLamViec(BacSi bs, XML3 xml3, HoSoYTe hs, String loai, ErrorKCBGroup group) {
    if (bs == null || xml3 == null || hs == null) return;

    String timeStr = loai.equalsIgnoreCase("chỉ định") ? xml3.getNgayYl() : xml3.getNgayThYl();
    if (timeStr == null || !timeStr.matches("\\d{12}")) return;

    try {
        LocalDateTime thoiGian = LocalDateTime.parse(timeStr, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

        if (!bs.trongGioLam(thoiGian)) {
            // Chuyển ngày sang định dạng dd/MM/yyyy HH:mm
            String timeDisplay = thoiGian.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            // Xác định ngày trong tuần tiếng Việt
            String[] thuViet = {"Chủ Nhật", "Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7"};
            String ngayTrongTuan = thuViet[thoiGian.getDayOfWeek().getValue() % 7]; // getValue() 1=Monday,...7=Sunday

            // Xác định buổi làm việc
            int gio = thoiGian.getHour();
            String buoi = (gio < 12) ? "sáng" : (gio < 18) ? "chiều" : "tối";

            ErrorKCBDetail detail = new ErrorKCBDetail();
            detail.setMaLk(hs.getMaLk());
            detail.setMaBn(hs.getMaBN());
            detail.setMaDichVu(xml3.getMaDichVu());
            detail.setTenDichVu(xml3.getTenDichVu());
            detail.setNgayYL(xml3.getNgayYl());
            detail.setNgayTHYL(xml3.getNgayThYl());
            detail.setNgaykq(xml3.getNgayKq());
            detail.setMaBsCĐ(xml3.getMaBacSi());
            detail.setMaBsTH(bs.getMaBS());
            detail.setErrorDetail("⛔ Bác sĩ " + bs.getHoTenBS() +
                    " không làm việc vào " + loai + " lúc " + timeDisplay +
                    " (" + ngayTrongTuan + ", buổi " + buoi + ")");
            group.addError(detail);
        }
    } catch (Exception e) {
        // Bỏ qua lỗi parse
    }
}

    // -------------------------------------------------------------------

public static List<ErrorKCBGroup> ErrorKCB(List<HoSoYTe> hsytList) {
    List<ErrorKCBGroup> groupedErrors = new ArrayList<>();
    Set<String> khoaChinhSet = Set.of("02.03", "03.18", "10.19"); 
    List<BacSi> dsBacSi = BacSi_data.getDsBacSi();

    for (HoSoYTe hs : hsytList) {
        String maLK = hs.getMaLk();
        ErrorKCBGroup group = new ErrorKCBGroup(maLK);

        // 🔹 1. Tìm dịch vụ chính trong hồ sơ
        XML3 dvChinh = hs.getDsCLS().stream()
                .filter(x -> khoaChinhSet.contains(norm(x.getMaDichVu())))
                .findFirst()
                .orElse(null);
        if (dvChinh == null) continue;
        if ("08.19".equals(norm(dvChinh.getMaDichVu()))) continue;

        // 🔹 1.1 Kiểm tra bác sĩ chính phải trùng với người thực hiện
        String bsChiDinh = norm(dvChinh.getMaBacSi());
        String bsThucHien = norm(dvChinh.getNguoiThucHien());

        if (bsChiDinh == null || bsThucHien == null || !bsChiDinh.equals(bsThucHien)) {
            ErrorKCBDetail detail = new ErrorKCBDetail();
            detail.setMaLk(hs.getMaLk());
            detail.setMaBn(hs.getMaBN());
            detail.setMaDichVu(dvChinh.getMaDichVu());
            detail.setTenDichVu(dvChinh.getTenDichVu());
            detail.setMaBsCĐ(bsChiDinh);
            detail.setMaBsTH(bsThucHien);
            detail.setErrorDetail("Bác sĩ chỉ định và thực hiện dịch vụ chính phải trùng nhau");
            group.addError(detail);
        }

        String bsChinh = norm(dvChinh.getMaBacSi());

        // 🔹 2. Duyệt từng dịch vụ kỹ thuật trong hồ sơ
        for (XML3 xml3 : hs.getDsCLS()) {

            // ✅ Kiểm tra thiếu bác sĩ chỉ định hoặc thực hiện
            if ((xml3.getMaBacSi() == null || xml3.getMaBacSi().isBlank()) ||
                (xml3.getNguoiThucHien() == null || xml3.getNguoiThucHien().isBlank())) {

                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaBn(hs.getMaBN());
                detail.setMaDichVu(xml3.getMaDichVu());
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());

                if (xml3.getMaBacSi() == null || xml3.getMaBacSi().isBlank()) {
                    detail.setErrorDetail("Thiếu bác sĩ chỉ định");
                } else {
                    detail.setErrorDetail("Thiếu bác sĩ thực hiện");
                }

                group.addError(detail);
                continue; // bỏ qua dịch vụ này nếu thiếu thông tin bác sĩ
            }

            // 🔹 Chuẩn hóa thông tin bác sĩ
            String maBS = norm(xml3.getMaBacSi());
            String maNguoiThucHien = norm(xml3.getNguoiThucHien());
            String idToCheck = maNguoiThucHien != null ? maNguoiThucHien : maBS;

            // 🔹 3. Kiểm tra BS chỉ định có khớp với BS chính không
            if (bsChinh != null && (maBS == null || !maBS.equals(bsChinh))) {
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaBn(hs.getMaBN());
                detail.setMaDichVu(xml3.getMaDichVu());
                detail.setMaBsCĐ(bsChinh);
                detail.setMaBsTH(maBS);
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());
                detail.setErrorDetail("BS chỉ định không khớp với BS chính");
                group.addError(detail);
            }

            // 🔹 4. Kiểm tra xem bác sĩ thực hiện có tồn tại trong danh sách không
            BacSi performer = findBacSiById(dsBacSi, idToCheck);
            if (performer == null) {
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaBn(hs.getMaBN());
                detail.setMaDichVu(xml3.getMaDichVu());
                detail.setMaBsTH(idToCheck);
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());
                detail.setErrorDetail("Không tìm thấy bác sĩ thực hiện");
                group.addError(detail);
                continue;
            }
            
            checkGioLamViec(performer, xml3, hs, "chỉ định", group);
            checkGioLamViec(performer, xml3, hs, "thực hiện", group);

            // 🔹 5. Kiểm tra chuyên môn của bác sĩ với dịch vụ
            DichVuKyThuat allowed = performer.getDsDichVuDuocPhep().stream()
                    .filter(d -> norm(d.getMaDV()).equals(norm(xml3.getMaDichVu())))
                    .findFirst()
                    .orElse(null);

            if (allowed == null) {
                ErrorKCBDetail detail = new ErrorKCBDetail();
                detail.setMaLk(maLK);
                detail.setMaBn(hs.getMaBN());
                detail.setMaDichVu(xml3.getMaDichVu());
                detail.setTenDichVu(xml3.getTenDichVu());
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgaykq(xml3.getNgayKq());
                detail.setMaBsCĐ(xml3.getMaBacSi());
                detail.setMaBsTH(idToCheck);
                detail.setErrorDetail("Bác sĩ không có chuyên môn làm DV này");
                group.addError(detail);
                continue;
            }
            
            LocalDateTime kqCongKham = null;
            try {
                if (dvChinh.getNgayKq() != null) {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
                    kqCongKham = LocalDateTime.parse(dvChinh.getNgayKq(), fmt);
                }
            } catch (Exception e) {
                // ignore
            }

            // 🔹 6. Kiểm tra thời gian hợp lệ của từng dịch vụ
            checkThoiGian(xml3, allowed, maLK, group,kqCongKham);

        }

        // 🔹 7. Kiểm tra đồng bộ thời gian giữa các dịch vụ & thuốc
        checkThoiGianDongBo(hs.getDsCLS(), maLK, group);
        checkThuocSauKQ(hs, group);
        checkBacSiChiDinhTrungGio(hs, group); 

        // 🔹 8. Nếu hồ sơ có lỗi thì thêm vào danh sách kết quả
        if (!group.getErrors().isEmpty()) {
            groupedErrors.add(group);
        }
    }

    return groupedErrors;
}

}





    

   





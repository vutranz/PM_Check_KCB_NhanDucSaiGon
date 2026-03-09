package com.thecode.WebSite_CHECK_XML.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
import java.time.LocalDate;

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

    // ❌ Nếu có 08.16 (điều trị ngoại trú nhiều ngày) → bỏ qua toàn bộ kiểm tra
    boolean hasDieuTriNgoaiTru = dsCLS.stream()
            .anyMatch(x -> "08.16".equals(norm(x.getMaDichVu())));

    if (hasDieuTriNgoaiTru) return;
    
    Set<String> skipSet = Set.of("02.03", "03.18", "10.19");

    List<XML3> validCLS = dsCLS.stream()
            .filter(xml3 -> !skipSet.contains(norm(xml3.getMaDichVu())))
            .collect(Collectors.toList());

    if (validCLS.isEmpty()) return;

    String firstNgayYL = validCLS.get(0).getNgayYl();
    //String firstThYl = validCLS.get(0).getNgayThYl();

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
    }
}



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
            "10.19".equals(maDv)||
            "08.16".equals(maDv);;

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
                    detail.setNgayTHYL(xml3.getNgayThYl());
                    detail.setNgaykq(xml3.getNgayKq());
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
                detail.setMaBsTH(xml3.getNguoiThucHien());
                detail.setMaBsCĐ(xml3.getMaBacSi());
                detail.setNgayTHYL(xml3.getNgayThYl());
                detail.setNgayYL(xml3.getNgayYl());
                detail.setNgaykq(xml3.getNgayKq());
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
    Set<String> khoaChinhSet = Set.of("02.03", "03.18", "10.19","08.16"); 
    List<BacSi> dsBacSi = BacSi_data.getDsBacSi();
    Map<String, Map<LocalDate, Integer>> soCaTheoBacSi = new HashMap<>();
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
    for (HoSoYTe hs : hsytList) {
        String maLK = hs.getMaLk();
        ErrorKCBGroup group = new ErrorKCBGroup(maLK);

        // 🔹 1. Tìm dịch vụ chính trong hồ sơ
        XML3 dvChinh = hs.getDsCLS().stream()
                .filter(x -> khoaChinhSet.contains(norm(x.getMaDichVu())))
                .findFirst()
                .orElse(null);
        if (dvChinh == null) continue;

        // 🔹 Đếm số ca khám theo bác sĩ / ngày
        try {
            String cchn = norm(dvChinh.getMaBacSi());

            if (dvChinh.getNgayKq() != null && cchn != null) {
                LocalDate ngay = LocalDateTime.parse(dvChinh.getNgayKq(), fmt).toLocalDate();

                soCaTheoBacSi
                    .computeIfAbsent(cchn, k -> new HashMap<>())
                    .merge(ngay, 1, Integer::sum);
            }
        } catch (Exception e) {
            // ignore
        }
        //if ("08.16".equals(norm(dvChinh.getMaDichVu()))) continue;

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
                    kqCongKham = LocalDateTime.parse(dvChinh.getNgayKq(), fmt);
                }
            } catch (Exception e) {
            }

            // 🔹 6. Kiểm tra thời gian hợp lệ của từng dịch vụ
            checkThoiGian(xml3, allowed, maLK, group,kqCongKham);

        }

        // 🔹 7. Kiểm tra đồng bộ thời gian giữa các dịch vụ & thuốc
        checkThoiGianDongBo(hs.getDsCLS(), maLK, group);

//         if (!"08.16".equals(norm(dvChinh.getMaDichVu()))) {
//         checkThuocSauKQ(hs, group);
// }
        checkThuocSauKQ(hs, group);

        // 🔹 8. Nếu hồ sơ có lỗi thì thêm vào danh sách kết quả
        if (!group.getErrors().isEmpty()) {
            groupedErrors.add(group);
        }
    }
        for (Map.Entry<String, Map<LocalDate, Integer>> bsEntry : soCaTheoBacSi.entrySet()) {

    String cchn = bsEntry.getKey();

    for (Map.Entry<LocalDate, Integer> ngayEntry : bsEntry.getValue().entrySet()) {

        int soCa = ngayEntry.getValue();

        // 🔹 In ra để kiểm tra
        System.out.println("CCHN: " + cchn +
                           " | Ngày: " + ngayEntry.getKey() +
                           " | Số ca: " + soCa);

        if (soCa > 65) {

            ErrorKCBGroup group = new ErrorKCBGroup("SYSTEM");

            ErrorKCBDetail detail = new ErrorKCBDetail();
            detail.setMaBsCĐ(cchn);
            detail.setErrorDetail(
                "Bác sĩ có CCHN " + cchn +
                " khám " + soCa +
                " ca trong ngày " + ngayEntry.getKey()
            );

            group.addError(detail);
            groupedErrors.add(group);
        }
    }
}

    return groupedErrors;
}

}





    

   





package com.thecode.WebSite_CHECK_XML.Controller;

import java.util.ArrayList;
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
import com.thecode.WebSite_CHECK_XML.Service.BacSi_data;


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


private static void checkBacSiChiDinhTrungGio(HoSoYTe hs, ErrorKCBGroup group) {
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    List<XML3> dsCLS = hs.getDsCLS();

    for (XML3 dv : dsCLS) {
        if (dv.getMaBacSi() == null || dv.getNgayYl() == null || dv.getNgayKq() == null)
            continue;

        try {
            LocalDateTime start = LocalDateTime.parse(dv.getNgayYl(), fmt);
            LocalDateTime end = LocalDateTime.parse(dv.getNgayKq(), fmt);
            String maBSChiDinh = dv.getMaBacSi();

            // Tìm các DV khác mà BS này đang thực hiện (không phải DV hiện tại)
            for (XML3 other : dsCLS) {
                if (other == dv) continue;

                boolean laCungBacSi = maBSChiDinh.equals(other.getNguoiThucHien())
                        || maBSChiDinh.equals(other.getMaBacSi());

                if (!laCungBacSi) continue;
                if (other.getNgayYl() == null || other.getNgayKq() == null) continue;

                LocalDateTime oStart = LocalDateTime.parse(other.getNgayYl(), fmt);
                LocalDateTime oEnd = LocalDateTime.parse(other.getNgayKq(), fmt);

                boolean overlap = !(end.isBefore(oStart) || oEnd.isBefore(start));

                if (overlap) {
                    ErrorKCBDetail detail = new ErrorKCBDetail();
                    detail.setMaLk(hs.getMaLk());
                    detail.setMaBn(hs.getMaBN());
                    detail.setMaDichVu(other.getMaDichVu());
                    detail.setTenDichVu(other.getTenDichVu());
                     detail.setNgayYL(other.getNgayYl());
                    detail.setNgayTHYL(other.getNgayThYl());
                    detail.setNgaykq(other.getNgayKq());
                    detail.setMaBsCĐ(maBSChiDinh);
                    detail.setMaBsTH(other.getNguoiThucHien());
                    detail.setErrorDetail("Bác sĩ chỉ định " + maBSChiDinh +
                            " có trùng giờ giữa dịch vụ \"" + dv.getTenDichVu() +
                            "\" và \"" + other.getTenDichVu() + "\"");
                    group.addError(detail);
                }
            }
        } catch (Exception ignored) {}
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



private static void checkThoiGian(XML3 xml3, DichVuKyThuat allowed, String maLK, ErrorKCBGroup group) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        try {
          
            if (xml3.getNgayThYl() != null && xml3.getNgayKq() != null) {
                LocalDateTime yl = LocalDateTime.parse(xml3.getNgayThYl(), fmt);
                LocalDateTime kq = LocalDateTime.parse(xml3.getNgayKq(), fmt);
                long diffMinutes = Duration.between(yl, kq).toMinutes();

                if (diffMinutes < allowed.getThoiGianToiThieu() || diffMinutes > allowed.getThoiGianToiDa()) {
                    ErrorKCBDetail detail = new ErrorKCBDetail();
                    detail.setMaLk(maLK);
                    detail.setMaDichVu(xml3.getMaDichVu());
                    //detail.setMaBn(xml3.getMaBN());
                    detail.setTenDichVu(xml3.getTenDichVu());
                    detail.setNgayYL(xml3.getNgayYl());
                    detail.setNgayTHYL(xml3.getNgayThYl());
                    detail.setNgaykq(xml3.getNgayKq());
                    detail.setMaBsCĐ(xml3.getMaBacSi());
                    detail.setMaBsTH(xml3.getNguoiThucHien());
                    detail.setErrorDetail("Thời gian DV " + allowed.getTenDV()
                            + " lệch " + diffMinutes + "p, chuẩn " + allowed.getThoiGianToiThieu() + "-" + allowed.getThoiGianToiDa());
                    group.addError(detail);
                }
            }
        } catch (Exception e) {
            // ErrorKCBDetail detail = new ErrorKCBDetail();
            // detail.setMaLk(maLK);
            // detail.setMaDichVu(xml3.getMaDichVu());
            // detail.setMaBsCĐ(xml3.getMaBacSi());
            // detail.setMaBsTH(xml3.getMaBacSi());
            // //detail.setErrorDetail("Lỗi xử lý thời gian DV " + xml3.getMaDichVu() + " (không parse được hoặc dữ liệu thiếu)");
            // group.addError(detail);
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

            // 🔹 6. Kiểm tra thời gian hợp lệ của từng dịch vụ
            checkThoiGian(xml3, allowed, maLK, group);
        }

        // 🔹 7. Kiểm tra đồng bộ thời gian giữa các dịch vụ & thuốc
        checkThoiGianDongBo(hs.getDsCLS(), maLK, group);
        checkThuocSauKQ(hs, group);
        //checkBacSiChiDinhTrungGio(hs, group); // có thể bật lại sau nếu cần

        // 🔹 8. Nếu hồ sơ có lỗi thì thêm vào danh sách kết quả
        if (!group.getErrors().isEmpty()) {
            groupedErrors.add(group);
        }
    }

    return groupedErrors;
}





}





    

   





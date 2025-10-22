package com.thecode.WebSite_CHECK_XML.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;
import com.thecode.WebSite_CHECK_XML.Model.application.DichVuKyThuat;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBGroup;
import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;
import com.thecode.WebSite_CHECK_XML.Service.BacSi_Data;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

public class Check_Error_KCB {

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
    List<BacSi> dsBacSi = BacSi_Data.getDsBacSi();

    for (HoSoYTe hs : hsytList) {
        String maLK = hs.getMaLk();
        ErrorKCBGroup group = new ErrorKCBGroup(maLK);

        // tìm DV chính
        XML3 dvChinh = hs.getDsCLS().stream()
                .filter(x -> khoaChinhSet.contains(norm(x.getMaDichVu())))
                .findFirst()
                .orElse(null);
        if (dvChinh == null) continue;
        if ("08.19".equals(norm(dvChinh.getMaDichVu()))) continue;

        String bsChinh = norm(dvChinh.getMaBacSi());

        for (XML3 xml3 : hs.getDsCLS()) {
            String maBS = norm(xml3.getMaBacSi());
            String maNguoiThucHien = norm(xml3.getNguoiThucHien());
            String idToCheck = maNguoiThucHien != null ? maNguoiThucHien : maBS;

            // 1) check BS chỉ định
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

            // 2) check performer
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

            // 3) check DV hợp lệ
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

            // 4) check thời gian DV (cá nhân từng dịch vụ)
            checkThoiGian(xml3, allowed, maLK, group);
        }

        // 5) check đồng bộ giờ YL & THYL của tất cả DV trong cùng hồ sơ
        checkThoiGianDongBo(hs.getDsCLS(), maLK, group);

        if (!group.getErrors().isEmpty()) {
            groupedErrors.add(group);
        }
    }

    return groupedErrors;
}




}





    

   





package com.thecode.WebSite_CHECK_XML.Controller;

public class test {
    /* 
    package com.thecode.WebSite_CHECK_XML.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;
import com.thecode.WebSite_CHECK_XML.Model.application.DichVuKyThuat;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;
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
    private static void checkThoiGian(XML3 xml3, DichVuKyThuat allowed, String maLK, List<String> errors) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        if (xml3.getNgayThYl() != null && xml3.getNgayKq() != null) {
            try {
                LocalDateTime yl = LocalDateTime.parse(xml3.getNgayThYl(), fmt);
                LocalDateTime kq = LocalDateTime.parse(xml3.getNgayKq(), fmt);

                long diffMinutes = Duration.between(yl, kq).toMinutes();

                if (diffMinutes < allowed.getThoiGianToiThieu() || diffMinutes > allowed.getThoiGianToiDa()) {
                    errors.add("❌ Lỗi MaLK " + maLK + ": Thời gian DV " + allowed.getTenDV()
                            + " không nằm trong chuẩn (chênh " + diffMinutes + "p, chuẩn "
                            + allowed.getThoiGianToiThieu() + "-" + allowed.getThoiGianToiDa() + "p)");
                }
            } catch (Exception e) {
                //errors.add("⚠️ Lỗi parse hoặc tính thời gian cho DV " + xml3.getMaDichVu());
            }
        }
    }
    // -------------------------------------------------------------------

    public static List<String> ErrorKCB(List<HoSoYTe> hsytList) {
        List<ErrorKCBDetail> errors = new ArrayList<>();
        Set<String> khoaChinhSet = Set.of("02.03", "03.18", "10.19"); // Dịch vụ chính
        List<BacSi> dsBacSi = BacSi_Data.getDsBacSi();

        Set<String> missingDoctors = new HashSet<>();
        Set<String> missingDvCodes = new HashSet<>();

        for (HoSoYTe hs : hsytList) {
            String maLK = hs.getMaLk();

            // 1) tìm dịch vụ chính
            XML3 dvChinh = hs.getDsCLS().stream()
                    .filter(x -> khoaChinhSet.contains(norm(x.getMaDichVu())))
                    .findFirst()
                    .orElse(null);
            if (dvChinh == null) {
                continue;
            }

            String maDVChinh = norm(dvChinh.getMaDichVu());
            if ("08.19".equals(maDVChinh)) {
                continue;
            }

            String bsChinh = dvChinh != null ? norm(dvChinh.getMaBacSi()) : null;

            for (XML3 xml3 : hs.getDsCLS()) {
                
                String maBS = norm(xml3.getMaBacSi());
                String maNguoiThucHien = norm(xml3.getNguoiThucHien());
                String idToCheck = maNguoiThucHien != null ? maNguoiThucHien : maBS;

                // 2) check BS chỉ định
                if (bsChinh != null && (maBS == null || !maBS.equals(bsChinh))) {
                    errors.add("❌ Lỗi MaLK " + maLK +
                            ": BS chỉ định không khớp với BS chính (" + bsChinh + " vs " + maBS + ") tại DV " + xml3.getMaDichVu());
                }

                // 3) check bác sĩ performer
                BacSi performer = findBacSiById(dsBacSi, idToCheck);
                if (performer == null) {
                    missingDoctors.add(idToCheck);
                    errors.add("❌ Lỗi MaLK " + maLK + ": Không tìm thấy bác sĩ " + idToCheck + " (DV " + xml3.getMaDichVu() + ")");
                    continue;
                }

                // 4) check dịch vụ
                DichVuKyThuat allowed = performer.getDsDichVuDuocPhep().stream()
                        .filter(d -> norm(d.getMaDV()).equals(norm(xml3.getMaDichVu())))
                        .findFirst()
                        .orElse(null);

                if (allowed == null) {
                    errors.add("❌ Lỗi MaLK " + maLK + ": Bác sĩ " + performer.getHoTenBS()
                            + " (thực hiện=" + idToCheck + ") không có chuyên môn để làm DV " + xml3.getMaDichVu()
                            + " (chỉ định bởi " + maBS + ")");
                    missingDvCodes.add(xml3.getMaDichVu());
                    continue;
                }

                // 5) check thời gian DV (NEW)
                checkThoiGian(xml3, allowed, maLK, errors);
            }
        }

        return errors;
    }
}





    

   */





}

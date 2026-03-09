package com.thecode.WebSite_CHECK_XML.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;
import com.thecode.WebSite_CHECK_XML.Model.application.CaLamViec;

public class BacSi_data {

    public static List<BacSi> getDsBacSi() {

        /*Bác Sĩ */
        BacSi bsThanh = new BacSi("000003/LA-GPHN", "Bs.Thành", "PK1", DichVuKyThuat_data.pvthanh());
        bsThanh.setLichLamViec(getLichBsThanh());

        BacSi bsLe = new BacSi("000139/BTH-CCHN", "Bs.Lễ", "PK3", DichVuKyThuat_data.lnle());
        bsLe.setLichLamViec(getLichBsLe());

        BacSi bsDuongDuyDat = new BacSi("009359/QNA-CCHN", "Bs.Duy Đạt", "Ngoại", DichVuKyThuat_data.ddDat());
        bsDuongDuyDat.setLichLamViec(getLichBsDDDat());
       
        BacSi bsNguyenThiGiangHanh = new BacSi("002220/BTH-CCHN", "Bs.Nguyễn Thị Giang Hạnh", "Nội,CĐHA", DichVuKyThuat_data.ntghanh());
        bsNguyenThiGiangHanh.setLichLamViec(getLichBSNguyenThiGiangHanh());

        /*YHCT-PHCN */
        BacSi Bs_TNM_Phuong = new BacSi("4172/BTH-CCHN", "Bs.Trần Ngọc Minh Phương", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        Bs_TNM_Phuong.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_NgoVuThai = new BacSi("000036/CM-GPHN", "KTV_YHCTPHCN_NgoVuThai", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_NgoVuThai.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_TranDinhLam = new BacSi("006619/KH-CCHN", "KTV_YHCTPHCN_TranDinhLam", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_TranDinhLam.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_PhamHoangHieu = new BacSi("6804/BTH-CCHN", "KTV_YHCTPHCN_PhamHoangHieu", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_PhamHoangHieu.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_PhamMinhThanh = new BacSi("000010/BTH-GPHN", "KTV_YHCTPHCN_PhamMinhThanh", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_PhamMinhThanh.setLichLamViec(getLichPhamMinhThanh());

        BacSi KTV_YHCTPHCN_ThongDinhHuong = new BacSi("4555/BTH-CCHN", "KTV_YHCTPHCN_ThongDinhHuong", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_ThongDinhHuong.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_TranThuyDiemTramg = new BacSi("000162/AG-GPHN", "KTV_YHCTPHCN_TranThuyDiemTrang", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_TranThuyDiemTramg.setLichLamViec(getLichTranNgocMinhPhuong());

        BacSi KTV_YHCTPHCN_NguyenTienVung = new BacSi("6457/BTH-CCHN", "KTV_YHCTPHCN_NguyenTienVung", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_NguyenTienVung.setLichLamViec(getLichTranNgocMinhPhuong());

         BacSi KTV_YHCTPHCN_NguThiThuHuong = new BacSi("000954/LĐ-GPHN", "KTV_YHCTPHCN_NguThiThuHuong", "YHCT-PHCN", DichVuKyThuat_data.YHCT_PHCN());
        KTV_YHCTPHCN_NguThiThuHuong.setLichLamViec(getLichTranNgocMinhPhuong());
        

        /*KTV CĐHA */

        BacSi bsHoai = new BacSi("001070/TB-CCHN", "Bs.Hoài", "SA", DichVuKyThuat_data.SieuAm());
        bsHoai.setLichLamViec(getLichBsHoai());

        BacSi ktvLuongQuocAnh = new BacSi("016852/TH-CCHN", "ktv.LuongQuocAnh", "Xquang", DichVuKyThuat_data.Xquang());
        ktvLuongQuocAnh.setLichLamViec(getLichKTVDuongQuocAnh());
        
        /*KTV Xét Nghiệm */

        BacSi KTVXetNghiemThao = new BacSi("4029/BTH-CCHN", "Thảo", "XN", DichVuKyThuat_data.getDsXetNghiem());
        KTVXetNghiemThao.setLichLamViec(getLichBsNguyenThiNgocThao());

        return Arrays.asList(bsThanh, bsLe, bsDuongDuyDat, bsHoai,
                            bsNguyenThiGiangHanh,
                            KTVXetNghiemThao, 
                            ktvLuongQuocAnh,
                            Bs_TNM_Phuong,KTV_YHCTPHCN_NgoVuThai,KTV_YHCTPHCN_TranDinhLam,KTV_YHCTPHCN_PhamHoangHieu,KTV_YHCTPHCN_PhamMinhThanh,KTV_YHCTPHCN_ThongDinhHuong, KTV_YHCTPHCN_TranThuyDiemTramg,KTV_YHCTPHCN_NguyenTienVung,KTV_YHCTPHCN_NguThiThuHuong);
    }

    // ---------- Lịch cụ thể cho từng bác sĩ ----------

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsThanh() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

        List<CaLamViec> caNgay = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),  // Ca sáng
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))  // Ca chiều
        );

        for (DayOfWeek day : List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY)) {
            lich.put(day, caNgay);
        }

        // Chủ nhật nghỉ
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());

        return lich;
    }



    private static Map<DayOfWeek, List<CaLamViec>> getLichBsLe() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

       
        List<CaLamViec> caNgay = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))
        );

        // Làm từ thứ 2 -> thứ 7
        for (DayOfWeek d : List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)) {
            lich.put(d, caNgay);
        }

        // Chủ nhật nghỉ
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());

        return lich;
    }


    private static Map<DayOfWeek, List<CaLamViec>> getLichBsDDDat() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

        CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
        CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

        // T2–T6: sáng + chiều
        for (DayOfWeek d : List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            lich.put(d, List.of(caSang, caChieu));
        }

        // T7 & CN: chỉ làm buổi sáng
        lich.put(DayOfWeek.SATURDAY, List.of(caSang));
        lich.put(DayOfWeek.SUNDAY, List.of(caSang));

        return lich;
    }


   private static Map<DayOfWeek, List<CaLamViec>> getLichBsHoai() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

        // Ca sáng và chiều
        CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
        CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

        // Thứ 2 - Thứ 7: làm cả ngày
        for (DayOfWeek d : List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)) {
            lich.put(d, List.of(caSang, caChieu));
        }

        // Chủ nhật: nghỉ
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());

        return lich;
    }



    private static Map<DayOfWeek, List<CaLamViec>> getLichKTVDuongQuocAnh() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

        // Ca sáng và chiều
        CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
        CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

        // Thứ 2 - Thứ 6: làm cả ngày
        for (DayOfWeek d : List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        )) {
            lich.put(d, List.of(caSang, caChieu));
        }

        // Thứ 7: nghỉ
        lich.put(DayOfWeek.SATURDAY, Collections.emptyList());

        // Chủ nhật: chỉ làm buổi sáng
        lich.put(DayOfWeek.SUNDAY, List.of(caSang));

        return lich;
    }


    private static Map<DayOfWeek, List<CaLamViec>> getLichBSNguyenThiGiangHanh() {
    Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

    // Ca sáng và chiều
    CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
    CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

    // Thứ 2 -> Thứ 6: làm cả ngày
    for (DayOfWeek d : List.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
    )) {
        lich.put(d, List.of(caSang, caChieu));
    }

    // Chủ nhật: làm cả ngày
    lich.put(DayOfWeek.SUNDAY, List.of(caSang, caChieu));

    // Thứ 7: nghỉ (không cấu hình)
    return lich;
}



    private static Map<DayOfWeek, List<CaLamViec>> getLichPhamMinhThanh() {
    Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

    // Thứ 2 - Thứ 6: 11h40 -> 13h20
    CaLamViec caTrua = new CaLamViec(
            LocalTime.of(11, 40),
            LocalTime.of(13, 20)
    );

    for (DayOfWeek d : List.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY)) {
        lich.put(d, List.of(caTrua));
    }

    // Thứ 7 & Chủ nhật: 07h00 -> 19h00
    CaLamViec caCaNgay = new CaLamViec(
            LocalTime.of(7, 0),
            LocalTime.of(19, 0)
    );

    lich.put(DayOfWeek.SATURDAY, List.of(caCaNgay));
    lich.put(DayOfWeek.SUNDAY, List.of(caCaNgay));

    return lich;
}

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsNguyenThiNgocThao() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

        // Ca sáng và chiều
        CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
        CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

        // Thứ 2 - Thứ 7: làm cả ngày
        for (DayOfWeek d : List.of(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY,
                DayOfWeek.SATURDAY
        )) {
            lich.put(d, List.of(caSang, caChieu));
        }

        // Chủ nhật: chỉ làm buổi sáng
        lich.put(DayOfWeek.SUNDAY, List.of(caSang));

        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichTranNgocMinhPhuong() {
    Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

    // Ca sáng và chiều
    CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
    CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

    // Thứ 2 - Thứ 7: làm cả ngày
    for (DayOfWeek d : List.of(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
    )) {
        lich.put(d, List.of(caSang, caChieu));
    }

    // Chủ nhật: nghỉ
    lich.put(DayOfWeek.SUNDAY, Collections.emptyList());

    return lich;
}

}

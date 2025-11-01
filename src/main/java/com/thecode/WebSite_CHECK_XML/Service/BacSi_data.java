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
        BacSi bsThanh = new BacSi("000003/LA-GPHN", "Bs.Thành", "PK1", DichVuKyThuat_data.pvthanh());
        bsThanh.setLichLamViec(getLichBsThanh());

        BacSi bsNhatAnh = new BacSi("0034449/HCM-CCHN", "Bs.Nhất Anh", "PK2", DichVuKyThuat_data.hnnanh());
        bsNhatAnh.setLichLamViec(getLichBsNhatAnh());

        BacSi bsLe = new BacSi("000139/BTH-CCHN", "Bs.Lễ", "PK3", DichVuKyThuat_data.lnle());
        bsLe.setLichLamViec(getLichBsLe());

        BacSi bsTuanAnh = new BacSi("009359/QNA-CCHN", "Bs.Duy Đạt", "Ngoại", DichVuKyThuat_data.ddDat());
        bsTuanAnh.setLichLamViec(getLichBsTuanAnh());

        BacSi bsHoai = new BacSi("001070/TB-CCHN", "Bs.Hoài", "SA", DichVuKyThuat_data.SieuAm());
        bsHoai.setLichLamViec(getLichBsHoai());

        BacSi ktvPhamPhuLam = new BacSi("008685/BD-CCHN", "ktv.PhamPhuLam", "Xquang", DichVuKyThuat_data.Xquang());
        ktvPhamPhuLam.setLichLamViec(getLichKtvPhamPhuLam());

        BacSi bsQuyen = new BacSi("008003/BD-CCHN", "Quyền", "XN", DichVuKyThuat_data.getDsXetNghiem());
        bsQuyen.setLichLamViec(getLichBsQuyen());

        return Arrays.asList(bsThanh, bsNhatAnh, bsLe, bsTuanAnh, bsHoai, bsQuyen,ktvPhamPhuLam);
    }

    // ---------- Lịch cụ thể cho từng bác sĩ ----------

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsThanh() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        List<CaLamViec> caTuan = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))
        );
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            lich.put(d, caTuan);
        }
        lich.put(DayOfWeek.SATURDAY, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());
        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsNhatAnh() {
    Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();

    CaLamViec caSang = new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30));
    CaLamViec caChieu = new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0));

    // T2–T6 + CN: làm cả ngày
    for (DayOfWeek d : List.of(
            DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)) {
        lich.put(d, List.of(caSang, caChieu));
    }

    // Thứ 7: nghỉ
    lich.put(DayOfWeek.SATURDAY, Collections.emptyList());

    return lich;
}

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsLe() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        List<CaLamViec> caNgay = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 0)),
            new CaLamViec(LocalTime.of(13, 0), LocalTime.of(17, 0))
        );
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)) {
            lich.put(d, caNgay);
        }
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());
        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsTuanAnh() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        List<CaLamViec> caNgay = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))
        );
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)) {
            lich.put(d, caNgay);
        }
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());
        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsHoai() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        List<CaLamViec> caTuan = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))
        );
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            lich.put(d, caTuan);
        }
        lich.put(DayOfWeek.SATURDAY, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        lich.put(DayOfWeek.SUNDAY, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichKtvPhamPhuLam() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        List<CaLamViec> caTuan = Arrays.asList(
            new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30)),
            new CaLamViec(LocalTime.of(13, 30), LocalTime.of(17, 0))
        );
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY)) {
            lich.put(d, caTuan);
        }
        lich.put(DayOfWeek.SATURDAY, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        lich.put(DayOfWeek.SUNDAY, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        return lich;
    }

    private static Map<DayOfWeek, List<CaLamViec>> getLichBsQuyen() {
        Map<DayOfWeek, List<CaLamViec>> lich = new HashMap<>();
        for (DayOfWeek d : List.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                                   DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY)) {
            lich.put(d, List.of(new CaLamViec(LocalTime.of(7, 0), LocalTime.of(11, 30))));
        }
        lich.put(DayOfWeek.SUNDAY, Collections.emptyList());
        return lich;
    }
}

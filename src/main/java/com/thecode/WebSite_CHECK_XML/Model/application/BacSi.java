package com.thecode.WebSite_CHECK_XML.Model.application;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BacSi {
    private String maBS;
    private String hoTenBS;
    private String chuyenKhoaChinh;
    private List<DichVuKyThuat> dsDichVuDuocPhep;
    private Map<DayOfWeek, List<CaLamViec>> lichLamViec; // thêm


    public BacSi(String maBS, String hoTenBS, String chuyenKhoaChinh, List<DichVuKyThuat> dsDichVuDuocPhep) {
        this.maBS = maBS;
        this.hoTenBS = hoTenBS;
        this.chuyenKhoaChinh = chuyenKhoaChinh;
        this.dsDichVuDuocPhep = dsDichVuDuocPhep;
    }
    public boolean coTheThucHien(String maDV) {
        return dsDichVuDuocPhep.stream()
                .anyMatch(dv -> dv.getMaDV().equals(maDV));
    }

    // ✅ kiểm tra giờ làm việc
    public boolean trongGioLam(LocalDateTime thoiDiem) {
        if (lichLamViec == null) return false;
        List<CaLamViec> caTrongNgay = lichLamViec.get(thoiDiem.getDayOfWeek());
        if (caTrongNgay == null) return false;

        LocalTime gio = thoiDiem.toLocalTime();
        return caTrongNgay.stream().anyMatch(ca -> ca.trongKhoang(gio));
    }
}


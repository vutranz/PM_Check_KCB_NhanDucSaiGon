package com.thecode.WebSite_CHECK_XML.Model.application;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaLamViec {
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;

    // ✅ Đổi tên hàm cho khớp với BacSi.trongGioLam()
    public boolean trongKhoang(LocalTime time) {
        return !time.isBefore(gioBatDau) && !time.isAfter(gioKetThuc);
    }

    public LocalTime getGioBatDau() { return gioBatDau; }
    public LocalTime getGioKetThuc() { return gioKetThuc; }
}
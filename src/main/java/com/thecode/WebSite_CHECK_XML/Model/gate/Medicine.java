package com.thecode.WebSite_CHECK_XML.Model.gate;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    private Integer stt;
    private String maThuoc;
    private String tenHoatChat;
    private String tenThuoc;
    private String donViTinh;
    private String hamLuong;
    private String duongDung;
    private String maDuongDung;
    private String dangBaoChe;
    private String soDangKy;
    private BigDecimal soLuong;
    private BigDecimal donGia;
    private BigDecimal donGiaBh;
    private String quyCach;
    private String nhaSx;
    private String nuocSx;
    private String nhaThau;
    private String ttThau;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private String maCskcb;
    private String loaiThuoc;
    private String loaiThau;
    private String htThau;
    private String maDvkt;
    private String tccl;
    private String boPhanVt;
    private String tenKhoaHoc;
    private String nguonGoc;
    private String ppCheBien;
    private String maDlNhap;
    private String maDlCb;
    private BigDecimal tlhHcb;
    private BigDecimal tlhHbq;
    private LocalDate denNgay2; // cột DENNGAY
    private String id;
}

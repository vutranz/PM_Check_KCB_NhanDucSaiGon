package com.thecode.WebSite_CHECK_XML.Model.gate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVuKyThuat {
    private int stt;
    private String maTuongDuong;
    private String tenDvktPheduyet;
    private String tenDvktGia;
    private String phanLoaiPttt;
    private double donGia;
    private String ghiChu;
    private String quyetDinh;
    private String tuNgay;
    private String denNgay;
    private int id;
}

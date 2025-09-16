package com.thecode.WebSite_CHECK_XML.Model.application;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class XML1 {
    private String maLk;
    private String stt;
    private String maBn;
    private String hoTen;
    private String soCccd;
    private String ngaySinh;
    private String gioiTinh;
    private String nhomMau;
    private String maQuocTich;
    private String maDanToc;
    private String maNgheNghiep;
    private String diaChi;
    private String maTinhCuTru;
    private String maHuyenCuTru;
    private String maXaCuTru;
    private String dienThoai;
    private String maTheBhyt;
    private String maDkbd;
    private String gtTheTu;
    private String gtTheDen;
    private String ngayMienCct;
    private String lyDoVv;
    private String lyDoVnt;
    private String maLyDoVnt;
    private String chanDoanVao;
    private String chanDoanRv;
    private String maBenhChinh;
    private String maBenhKt;
    private String maBenhYhct;
    private String maPtttQt;
    private String maDoiTuongKcb;
    private String maNoiDi;
    private String maNoiDen;
    private String maTaiNan;
    private String ngayVao;
    private String ngayVaoNoiTru;
    private String ngayRa;
    private String giayChuyenTuyen;
    private Integer soNgayDtri;
    private String ppDieuTri;
    private String ketQuaDtri;
    private String maLoaiRv;
    private String ghiChu;
    private String ngayTtoan;
    private Double tThuoc;
    private Double tVtyt;
    private Double tTongchiBv;
    private Double tTongchiBh;
    private Double tBntt;
    private Double tBncct;
    private Double tBhtt;
    private Double tNguonKhac;
    private Double tBhttGdv;
    private Integer namQt;
    private Integer thangQt;
    private String maLoaiKcb;
    private String maKhoa;
    private String maCskcb;
    private String maKhuvuc;
    private Double canNang;
    private Double canNangCon;
    private Integer namNamLienTuc;
    private String ngayTaiKham;
    private String maHsba;
    private String maTtdv;
    private String duPhong;

    public XML1( String maLk, String stt,String maBn)
    {
        this.maLk=maLk;
        this.stt=stt;
        this.maBn=maBn;
    }
}

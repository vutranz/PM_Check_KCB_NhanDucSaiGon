package com.thecode.WebSite_CHECK_XML.Model.application;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoSoYTe {
    private String maLk;
    private String maBN;
    private String tenBenhNhan;
    private String ngayVao;
    private String ngayRa;
    private String ngayTT;
    private List<XML2> dsThuoc;
    private List<XML3> dsCLS;
}

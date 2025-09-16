package com.thecode.WebSite_CHECK_XML.Model.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVuKyThuat {
    private String maDV;
    private String tenDV;
    private int thoiGianToiThieu;
    private int thoiGianToiDa;
    private String typeDVKT;
}

package com.thecode.WebSite_CHECK_XML.Service;

import java.util.Arrays;
import java.util.List;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;

public class BacSi_data {
    private BacSi bsNoi;

    public BacSi_data() {
       
        bsNoi = new BacSi();
        bsNoi.setMaBS("BS001");
        bsNoi.setHoTenBS("Phạm Văn Thành");
        bsNoi.setChuyenKhoaChinh("02.03");
        bsNoi.setDsDichVuDuocPhep(Arrays.asList(xquang, sieuAm, xnMau));
    }

}


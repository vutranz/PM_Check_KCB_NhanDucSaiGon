package com.thecode.WebSite_CHECK_XML.Service;

import java.util.Arrays;
import java.util.List;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;

public class BacSi_Data {

    public static List<BacSi> getDsBacSi() {
        return Arrays.asList(
            new BacSi("000003/LA-GPHN", "Phạm Văn Thành", "02.03", DichVuKyThuat_data.pvthanh()),
            new BacSi("0034449/HCM-CCHN", "Hoàng Nguyễn Nhất Anh", "02.03", DichVuKyThuat_data.hnnanh()),
            new BacSi("0004002/BL-CCHN", "Lê Tuấn Anh", "10.19", DichVuKyThuat_data.ltanh()),
            new BacSi("000139/BTH-CCHN", "Lưu Ngọc Lễ", "03.18", DichVuKyThuat_data.lnle()),

            new BacSi("008003/BD-CCHN", "Lê Thị Lệ Quyền", "K47", DichVuKyThuat_data.getDsXetNghiem()),
            new BacSi("001070/TB-CCHN", "Nguyễn Ngọc Hoài", "K39", DichVuKyThuat_data.dsCDHA())
        );
    }
}

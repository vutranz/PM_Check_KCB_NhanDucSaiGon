package com.thecode.WebSite_CHECK_XML.Model.application;
import java.util.List;

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

    public boolean coTheThucHien(String maDV) {
        return dsDichVuDuocPhep.stream()
                .anyMatch(dv -> dv.getMaDV().equals(maDV));
    }
}


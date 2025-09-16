package com.thecode.WebSite_CHECK_XML.Model.gate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private String maKhoa;
    private String hoTen;
    private String maCchn;
    private List<String> phamViCm;
                 
}

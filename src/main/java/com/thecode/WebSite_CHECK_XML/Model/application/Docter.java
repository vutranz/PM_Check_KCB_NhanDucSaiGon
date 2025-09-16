package com.thecode.WebSite_CHECK_XML.Model.application;
import java.util.List;

import com.thecode.WebSite_CHECK_XML.Service.DocterService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Docter {
    private String hoTen;
    private String maCchn;
    private List<DocterService> phamViCm;
    
}

package com.thecode.WebSite_CHECK_XML.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocterService {
    private String maDv;
    private String tenDv;
    private int minTime;
    private int maxTime;
}

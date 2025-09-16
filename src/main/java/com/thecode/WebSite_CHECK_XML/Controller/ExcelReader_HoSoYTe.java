package com.thecode.WebSite_CHECK_XML.Controller;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML1;
import com.thecode.WebSite_CHECK_XML.Model.application.XML2;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;


public class ExcelReader_HoSoYTe {

   public static List<HoSoYTe> merge(List<XML1> xml1List, List<XML2> xml2List, List<XML3> xml3List) {
        List<HoSoYTe> result = new ArrayList<>();

        for (XML1 x1 : xml1List) {
            String maLK = x1.getMaLk();

             List<XML2> dsThuoc = xml2List.stream()
                    .filter(x3 -> x3.getMaLk().equals(maLK))
                    .collect(Collectors.toList());

            List<XML3> dsCLS = xml3List.stream()
                    .filter(x3 -> x3.getMaLk().equals(maLK))
                    .collect(Collectors.toList());

            HoSoYTe hoSo = new HoSoYTe(
                    x1.getMaLk(),
                    x1.getMaBn(),
                    x1.getHoTen(),
                    x1.getNgayVao(),
                    x1.getNgayRa(),
                    x1.getNgayTtoan(),
                    dsThuoc,
                    dsCLS
            );

            result.add(hoSo);
        }

        return result;
    }
}

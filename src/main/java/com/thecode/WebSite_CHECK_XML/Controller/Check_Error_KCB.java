package com.thecode.WebSite_CHECK_XML.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;

public class Check_Error_KCB {
 
    public static List<String> ErrorKCB(List<HoSoYTe> hsytList) {
        List<String> errors = new ArrayList<>();

        for (HoSoYTe hs : hsytList) {
        
            System.out.println(hs);
            System.out.println();
            
        }

            return errors;
    }
}

    

   





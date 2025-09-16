package com.thecode.WebSite_CHECK_XML;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thecode.WebSite_CHECK_XML.Controller.Check_Error_KCB;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML1;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML2;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML3;
import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML1;
import com.thecode.WebSite_CHECK_XML.Model.application.XML2;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;

@SpringBootApplication
public class WebSiteCheckXmlApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSiteCheckXmlApplication.class, args);
		List<XML1> xml1 = ExcelReader_XML1.readXML1("data_KCB_XML/4750_xml1.xls");
		List<XML2> xml2 = ExcelReader_XML2.readXML2("data_KCB_XML/4750_xml2.xls");
		List<XML3> xml3 = ExcelReader_XML3.readXML3("data_KCB_XML/4750_xml3.xls");
		List<HoSoYTe> hoSoList = ExcelReader_HoSoYTe.merge(xml1, xml2, xml3);
        List<String> s = Check_Error_KCB.ErrorKCB(hoSoList);

        for (String err : s) {
            System.out.println(err);
        }
	}

}

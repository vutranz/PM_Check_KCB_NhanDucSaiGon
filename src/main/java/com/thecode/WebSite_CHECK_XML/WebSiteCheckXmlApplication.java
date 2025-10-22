package com.thecode.WebSite_CHECK_XML;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.record.PageBreakRecord.Break;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.thecode.WebSite_CHECK_XML.Controller.Check_Error_KCB;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML1;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML2;
import com.thecode.WebSite_CHECK_XML.Controller.ExcelReader_XML3;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBGroup;
import com.thecode.WebSite_CHECK_XML.Model.application.HoSoYTe;
import com.thecode.WebSite_CHECK_XML.Model.application.XML1;
import com.thecode.WebSite_CHECK_XML.Model.application.XML2;
import com.thecode.WebSite_CHECK_XML.Model.application.XML3;
import com.thecode.WebSite_CHECK_XML.Service.ExcelWriter;

@SpringBootApplication
public class WebSiteCheckXmlApplication {

public static void main(String[] args) {
    SpringApplication.run(WebSiteCheckXmlApplication.class, args);

    List<XML1> xml1 = ExcelReader_XML1.readXML1("data_KCB_XML/4750_xml1.xls");
    List<XML2> xml2 = ExcelReader_XML2.readXML2("data_KCB_XML/4750_xml2.xls");
    List<XML3> xml3 = ExcelReader_XML3.readXML3("data_KCB_XML/4750_xml3.xls");
    List<HoSoYTe> hoSoList = ExcelReader_HoSoYTe.merge(xml1, xml2, xml3);
	List<ErrorKCBGroup> results = Check_Error_KCB.ErrorKCB(hoSoList);
	List<ErrorKCBDetail> errors = results.stream()
		.flatMap(group -> group.getErrors().stream())
		.collect(Collectors.toList());

	ExcelWriter.writeErrorsToExcel(errors, "src/main/resources/data_KCB_XML/errors_KCB.xlsx");
    System.exit(0);
}

}
//     List<ErrorKCBGroup> results = Check_Error_KCB.ErrorKCB(hoSoList);
// 	for (ErrorKCBGroup group : results) {
//     System.out.println("📌 MaLK = " + group.getMaLk());
//     for (ErrorKCBDetail detail : group.getErrors()) {
//         System.out.println("   - " + detail.getErrorDetail()
//                 + " (DV=" + detail.getMaDichVu() + ", BS chỉ định=" + detail.getMaBsCĐ() + ", BS thực hiện=" + detail.getMaBsTH() + ")" );
//     }
// }
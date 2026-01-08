package com.thecode.WebSite_CHECK_XML.Service;

import java.util.Arrays;
import java.util.List;

import com.thecode.WebSite_CHECK_XML.Model.application.DichVuKyThuat;

public class DichVuKyThuat_data {

    public static List<DichVuKyThuat> getDsXetNghiem() {
    return Arrays.asList(
            new DichVuKyThuat("22.0120.1370", "Tổng phân tích tế bào máu ngoại vi (máy đếm tổng trở)", 10, 15, "XETNGHIEM"),
            new DichVuKyThuat("23.0206.1596", "Tổng phân tích nước tiểu (máy tự động)", 10, 15, "XETNGHIEM"),

            new DichVuKyThuat("22.0138.1362", "Tìm ký sinh trùng sốt rét trong máu (thủ công)", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0003.1494", "Định lượng Acid Uric [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0010.1494", "Đo hoạt độ Amylase [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0019.1493", "Đo hoạt độ ALT (GPT) [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0020.1493", "Đo hoạt độ AST (GOT) [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0025.1493", "Định lượng Bilirubin trực tiếp [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0027.1493", "Định lượng Bilirubin toàn phần [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0030.1472", "Định lượng Canxi ion hóa [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0041.1506", "Định lượng Cholesterol toàn phần (máu)", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0051.1494", "Định lượng Creatinin (máu)", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0058.1487", "Điện giải đồ (Na, K, Cl) [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0075.1494", "Định lượng Glucose [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0077.1518", "Đo hoạt độ GGT [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0083.1523", "Định lượng HbA1c [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0084.1506", "Định lượng HDL-C [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0112.1506", "Định lượng LDL-C [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0147.1561", "Định lượng T3 [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0148.1561", "Định lượng T4 [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0158.1506", "Định lượng Triglycerid [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0161.1569", "Định lượng Troponin I [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0162.1570", "Định lượng TSH [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0166.1494", "Định lượng Urê máu [Máu]", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0228.1483", "Định lượng CRP", 23, 30, "XETNGHIEM"),
            new DichVuKyThuat("23.0244.1544", "Phản ứng CRP", 23, 30, "XETNGHIEM")
        );
    }

    public static List<DichVuKyThuat> SieuAm (){
    return Arrays.asList(

            new DichVuKyThuat("01.0002.1778", "Ghi điện tim cấp cứu tại giường", 5, 15, "CDHA"),

            new DichVuKyThuat("02.0314.0001", "Siêu âm ổ bụng", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0001.0001", "Siêu âm tuyến giáp", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0002.0001", "Siêu âm các tuyến nước bọt", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0003.0001", "Siêu âm cơ phần mềm vùng cổ mặt", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0004.0001", "Siêu âm hạch vùng cổ", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0012.0001", "Siêu âm thành ngực (cơ, phần mềm thành ngực)", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0015.0001", "Siêu âm ổ bụng (gan mật, tụy, lách, thận, bàng quang)", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0018.0001", "Siêu âm tử cung phần phụ", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0020.0001", "Siêu âm thai (thai, nhau thai, nước ối)", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0030.0001", "Siêu âm tử cung buồng trứng qua đường bụng", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0034.0001", "Siêu âm thai nhi trong 3 tháng đầu", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0035.0001", "Siêu âm thai nhi trong 3 tháng giữa", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0036.0001", "Siêu âm thai nhi trong 3 tháng cuối", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0043.0001", "Siêu âm khớp (gối, háng, khuỷu, cổ tay….)", 6, 15, "CDHA"),
            new DichVuKyThuat("18.0044.0001", "Siêu âm phần mềm (da, tổ chức dưới da, cơ….)", 6, 15, "CDHA")
         

           /*new DichVuKyThuat("18.0068.0028", "Chụp X-quang mặt thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0068.0029", "Chụp X-quang mặt thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0069.0028", "Chụp X-quang mặt thấp hoặc mặt cao [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0070.0028", "Chụp X-quang sọ tiếp tuyến [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0073.0028", "Chụp X-quang Hirtz [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0074.0028", "Chụp X-quang hàm chếch một bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0075.0028", "Chụp X-quang xương chính mũi nghiêng hoặc tiếp tuyến [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0077.0028", "Chụp X-quang Chausse III [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0078.0028", "Chụp X-quang Schuller [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0079.0028", "Chụp X-quang Stenvers [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0080.0028", "Chụp X-quang khớp thái dương hàm [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0085.0028", "Chụp X-quang mỏm trâm [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0086.0028", "Chụp X-quang cột sống cổ thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0086.0029", "Chụp X-quang cột sống cổ thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0087.0028", "Chụp X-quang cột sống cổ chếch hai bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0087.0029", "Chụp X-quang cột sống cổ chếch hai bên [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0088.0030", "Chụp X-quang cột sống cổ động, nghiêng 3 tư thế [số hóa 3 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0089.0028", "Chụp X-quang cột sống cổ C1-C2 [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0089.0029", "Chụp X-quang cột sống cổ C1-C2 [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0090.0028", "Chụp X-quang cột sống ngực thẳng nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0090.0029", "Chụp X-quang cột sống ngực thẳng nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0091.0028", "Chụp X-quang cột sống thắt lưng thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0091.0029", "Chụp X-quang cột sống thắt lưng thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0092.0028", "Chụp X-quang cột sống thắt lưng chếch hai bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0092.0029", "Chụp X-quang cột sống thắt lưng chếch hai bên [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0093.0028", "Chụp X-quang cột sống thắt lưng L5-S1 thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0093.0029", "Chụp X-quang cột sống thắt lưng L5-S1 thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0096.0028", "Chụp X-quang cột sống cùng cụt thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0096.0029", "Chụp X-quang cột sống cùng cụt thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0097.0030", "Chụp X-quang khớp cùng chậu thẳng chếch hai bên [số hóa 3 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0098.0028", "Chụp X-quang khung chậu thẳng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0099.0028", "Chụp X-quang xương đòn thẳng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0100.0028", "Chụp X-quang khớp vai thẳng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0101.0028", "Chụp X-quang khớp vai nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0102.0028", "Chụp X-quang xương bả vai thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0102.0029", "Chụp X-quang xương bả vai thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0103.0028", "Chụp X-quang xương cánh tay thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0103.0029", "Chụp X-quang xương cánh tay thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0104.0028", "Chụp X-quang khớp khuỷu thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0104.0029", "Chụp X-quang khớp khuỷu thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0106.0028", "Chụp X-quang xương cẳng tay thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0106.0029", "Chụp X-quang xương cẳng tay thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0108.0028", "Chụp X-quang xương bàn ngón tay thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0108.0029", "Chụp X-quang xương bàn ngón tay thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0112.0028", "Chụp X-quang khớp gối thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0112.0029", "Chụp X-quang khớp gối thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0114.0028", "Chụp X-quang xương cẳng chân thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0114.0029", "Chụp X-quang xương cẳng chân thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0115.0028", "Chụp X-quang xương cổ chân thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0115.0029", "Chụp X-quang xương cổ chân thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0116.0028", "Chụp X-quang xương bàn, ngón chân thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0116.0029", "Chụp X-quang xương bàn, ngón chân thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0119.0028", "Chụp X-quang ngực thẳng [số hóa 1 phim]", 6, 12, "CDHA")*/
        );
    }

    public static List<DichVuKyThuat> Xquang (){
    return Arrays.asList(

            new DichVuKyThuat("18.0068.0028", "Chụp X-quang mặt thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0068.0029", "Chụp X-quang mặt thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0069.0028", "Chụp X-quang mặt thấp hoặc mặt cao [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0070.0028", "Chụp X-quang sọ tiếp tuyến [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0073.0028", "Chụp X-quang Hirtz [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0074.0028", "Chụp X-quang hàm chếch một bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0075.0028", "Chụp X-quang xương chính mũi nghiêng hoặc tiếp tuyến [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0077.0028", "Chụp X-quang Chausse III [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0078.0028", "Chụp X-quang Schuller [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0079.0028", "Chụp X-quang Stenvers [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0080.0028", "Chụp X-quang khớp thái dương hàm [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0085.0028", "Chụp X-quang mỏm trâm [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0086.0028", "Chụp X-quang cột sống cổ thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0086.0029", "Chụp X-quang cột sống cổ thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0087.0028", "Chụp X-quang cột sống cổ chếch hai bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0087.0029", "Chụp X-quang cột sống cổ chếch hai bên [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0088.0030", "Chụp X-quang cột sống cổ động, nghiêng 3 tư thế [số hóa 3 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0089.0028", "Chụp X-quang cột sống cổ C1-C2 [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0089.0029", "Chụp X-quang cột sống cổ C1-C2 [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0090.0028", "Chụp X-quang cột sống ngực thẳng nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0090.0029", "Chụp X-quang cột sống ngực thẳng nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0091.0028", "Chụp X-quang cột sống thắt lưng thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0091.0029", "Chụp X-quang cột sống thắt lưng thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0092.0028", "Chụp X-quang cột sống thắt lưng chếch hai bên [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0092.0029", "Chụp X-quang cột sống thắt lưng chếch hai bên [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0093.0028", "Chụp X-quang cột sống thắt lưng L5-S1 thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0093.0029", "Chụp X-quang cột sống thắt lưng L5-S1 thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0096.0028", "Chụp X-quang cột sống cùng cụt thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0096.0029", "Chụp X-quang cột sống cùng cụt thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0097.0030", "Chụp X-quang khớp cùng chậu thẳng chếch hai bên [số hóa 3 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0098.0028", "Chụp X-quang khung chậu thẳng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0099.0028", "Chụp X-quang xương đòn thẳng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0100.0028", "Chụp X-quang khớp vai thẳng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0101.0028", "Chụp X-quang khớp vai nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0102.0028", "Chụp X-quang xương bả vai thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0102.0029", "Chụp X-quang xương bả vai thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0103.0028", "Chụp X-quang xương cánh tay thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0103.0029", "Chụp X-quang xương cánh tay thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0104.0028", "Chụp X-quang khớp khuỷu thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0104.0029", "Chụp X-quang khớp khuỷu thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0106.0028", "Chụp X-quang xương cẳng tay thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0106.0029", "Chụp X-quang xương cẳng tay thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0108.0028", "Chụp X-quang xương bàn ngón tay thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0108.0029", "Chụp X-quang xương bàn ngón tay thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0112.0028", "Chụp X-quang khớp gối thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0112.0029", "Chụp X-quang khớp gối thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0114.0028", "Chụp X-quang xương cẳng chân thẳng nghiêng [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0114.0029", "Chụp X-quang xương cẳng chân thẳng nghiêng [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0115.0028", "Chụp X-quang xương cổ chân thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0115.0029", "Chụp X-quang xương cổ chân thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0116.0028", "Chụp X-quang xương bàn, ngón chân thẳng, nghiêng hoặc chếch [số hóa 1 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0116.0029", "Chụp X-quang xương bàn, ngón chân thẳng, nghiêng hoặc chếch [số hóa 2 phim]", 6, 12, "CDHA"),
            new DichVuKyThuat("18.0119.0028", "Chụp X-quang ngực thẳng [số hóa 1 phim]", 6, 12, "CDHA")
        );
    }

    public static List<DichVuKyThuat> pvthanh(){
    return Arrays.asList(
            new DichVuKyThuat("02.03", "Khám Nội tổng hợp", 2, 3, "CDHA"),
            new DichVuKyThuat("02.0085.1778", "Điện tim thường", 4, 8, "CDHA"),
            new DichVuKyThuat("20.0080.0135", "Nội soi thực quản, dạ dày, tá tràng", 30, 31, "CDHA"),
           new DichVuKyThuat("18.0049.0004", "Siêu âm tim, màng tim qua thành ngực", 30, 45, "CDHA")
        );
    }

    public static List<DichVuKyThuat> hnnanh(){
    return Arrays.asList(
            new DichVuKyThuat("02.03", "Khám Nội tổng hợp", 2, 3, "CDHA"),
            new DichVuKyThuat("20.0080.0135", "Nội soi thực quản, dạ dày, tá tràng", 30, 31, "CDHA")
        );
    }

    public static List<DichVuKyThuat> lnle(){
    return Arrays.asList(
            new DichVuKyThuat("03.18", "Khám nhi", 2, 3, "CDHA")
        );
    }

    public static List<DichVuKyThuat> ltanh(){
    return Arrays.asList(
            new DichVuKyThuat("10.19", "Khám Ngoại tổng hợp", 2, 3, "CDHA")
        );
    }

    public static List<DichVuKyThuat> ddDat(){
    return Arrays.asList(
            new DichVuKyThuat("10.19", "Khám Ngoại tổng hợp", 2, 3, "CDHA")
        );
    }
}

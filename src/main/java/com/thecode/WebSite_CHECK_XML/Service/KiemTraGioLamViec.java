package com.thecode.WebSite_CHECK_XML.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.thecode.WebSite_CHECK_XML.Model.application.BacSi;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;
import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBGroup;

public class KiemTraGioLamViec {
    public static LocalDateTime parseExcelTime(String timeStr) {
        if (timeStr == null || timeStr.isBlank()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return LocalDateTime.parse(timeStr.trim(), formatter);
    }

    public static void kiemTra(BacSi bs, String maThoiGian, ErrorKCBGroup group) {
        LocalDateTime time = parseExcelTime(maThoiGian);
        if (time == null) return;

        if (!bs.trongGioLam(time)) {
            ErrorKCBDetail err = new ErrorKCBDetail();
            err.setMaBsCĐ(bs.getMaBS());
            err.setNgayYL(maThoiGian);
            err.setErrorDetail("❌ Bác sĩ không làm việc vào thời điểm " + time);
            group.addError(err);
        }
    }
}

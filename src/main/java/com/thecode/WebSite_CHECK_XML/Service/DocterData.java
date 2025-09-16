package com.thecode.WebSite_CHECK_XML.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


import com.thecode.WebSite_CHECK_XML.Model.application.Docter;
import com.thecode.WebSite_CHECK_XML.Model.gate.Doctor;

public class DocterData {
    public static List<Doctor> getDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        doctors.add(new Doctor("Lưu Ngọc Lễ", "000139/BTH-CCHN",
                Arrays.asList("03.18")));

        doctors.add(new Doctor("Hoàng Nguyễn Nhất Anh", "0034449/HCM-CCHN",
                Arrays.asList("02.03", "K39")));

        doctors.add(new Doctor("Lê Tuấn Anh", "0004002/BL-CCHN",
                Arrays.asList("10.19")));

        doctors.add(new Doctor("Nguyễn Ngọc Hoài", "001070/TB-CCHN",
                Arrays.asList("K39")));

        doctors.add(new Doctor("Lê Thị Lệ Quyền", "008003/BD-CCHN",
                Arrays.asList("K47")));

        doctors.add(new Doctor("Phạm Văn Thành", "000003/LA-GPHN",
                Arrays.asList("02.03", "K39")));

        return doctors;
    }

    public static List<DoctorService> getServices() {
        List<DoctorService> services = new ArrayList<>();

        services.add(new DoctorService("02.03", "Khám Nội tổng hợp", 2, 5));
        services.add(new DoctorService("03.18", "Khám Nhi", 2, 5));
        services.add(new DoctorService("10.19", "Khám Ngoại", 2, 5));
        services.add(new DoctorService("K39", "Siêu âm/Chẩn đoán hình ảnh", 2, 5));
        services.add(new DoctorService("K47", "Xét nghiệm", 2, 5));

        return services;
    }
}

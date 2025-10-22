package com.thecode.WebSite_CHECK_XML.Service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.thecode.WebSite_CHECK_XML.Model.application.ErrorKCBDetail;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelWriter {
    public static void writeErrorsToExcel(List<ErrorKCBDetail> errorList, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Errors");

        // Style cho header
        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        // Style cho datetime
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm"));

        // Header
        String[] headers = {"MaLK", "Mã BN", "Mã DV", "Tên DV",
                "BS Chỉ Định", "BS Thực Hiện", "Ngày YL", "Ngày THYL", "Ngày KQ", "Chi tiết lỗi"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Định dạng ngày giờ
        DateTimeFormatter inputFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        DateTimeFormatter outputFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Data rows
        int rowNum = 1;
        for (ErrorKCBDetail err : errorList) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(nvl(err.getMaLk()));
            row.createCell(1).setCellValue(nvl(err.getMaBn()));
            row.createCell(2).setCellValue(nvl(err.getMaDichVu()));
            row.createCell(3).setCellValue(nvl(err.getTenDichVu()));
            row.createCell(4).setCellValue(nvl(err.getMaBsCĐ()));
            row.createCell(5).setCellValue(nvl(err.getMaBsTH()));

            setDateCell(row.createCell(6), err.getNgayYL(), inputFmt, outputFmt, dateStyle);
            setDateCell(row.createCell(7), err.getNgayTHYL(), inputFmt, outputFmt, dateStyle);
            setDateCell(row.createCell(8), err.getNgaykq(), inputFmt, outputFmt, dateStyle);

            row.createCell(9).setCellValue(nvl(err.getErrorDetail()));
        }

        // Auto-size
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
            workbook.close();
            System.out.println("✅ Xuất lỗi ra Excel thành công: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String nvl(String s) {
        return s != null ? s : "";
    }

    private static void setDateCell(Cell cell, String rawValue,
                                    DateTimeFormatter inputFmt,
                                    DateTimeFormatter outputFmt,
                                    CellStyle style) {
        if (rawValue != null && !rawValue.isEmpty()) {
            try {
                LocalDateTime dt = LocalDateTime.parse(rawValue, inputFmt);
                cell.setCellValue(outputFmt.format(dt));
                cell.setCellStyle(style);
            } catch (Exception e) {
                // Nếu parse fail thì để nguyên
                cell.setCellValue(rawValue);
            }
        } else {
            cell.setCellValue("");
        }
    }
}

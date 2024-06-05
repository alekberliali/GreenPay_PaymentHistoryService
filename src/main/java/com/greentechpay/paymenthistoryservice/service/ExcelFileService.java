package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

@Service
public class ExcelFileService {
    protected void dataToExcel(List<PaymentHistory> paymentHistoryList) throws IOException {
        File file = new File("C:\\Users\\Ali\\Desktop\\PaymentHistory.xlsx");
        Workbook workbook;
        Sheet sheet;

        if (file.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheetAt(0);
            fileInputStream.close();

        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Payment History");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("TransactionId");
            headerRow.createCell(1).setCellValue("SenderRequestId");
            headerRow.createCell(2).setCellValue("Receiver");
            headerRow.createCell(3).setCellValue("Date");
            headerRow.createCell(4).setCellValue("Amount");
            headerRow.createCell(5).setCellValue("Currency");
            headerRow.createCell(6).setCellValue("PaymentMethod");
            headerRow.createCell(7).setCellValue("Status");
        }

        int lastRowNum = sheet.getLastRowNum()+1;
        for (PaymentHistory ph : paymentHistoryList) {
            Row row = sheet.createRow(lastRowNum++);
            row.createCell(0).setCellValue(ph.getTransactionId());
            row.createCell(1).setCellValue(ph.getSenderRequestId());
            if (ph.getToUser() != null) {
                row.createCell(2).setCellValue(ph.getToUser());
            }
            row.createCell(3).setCellValue(ph.getPaymentDate());
            row.createCell(4).setCellValue(ph.getAmount().doubleValue());
            row.createCell(5).setCellValue(ph.getCurrency().toString());
            row.createCell(6).setCellValue(ph.getTransferType().toString());
            row.createCell(7).setCellValue(ph.getStatus().toString());
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Ali\\Desktop\\PaymentHistory.xlsx")) {
            workbook.write(fileOutputStream);
        }
        workbook.close();
    }
}



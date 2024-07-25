package com.greentechpay.paymenthistoryservice.controller;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;


    @PostMapping(value = "/filter")
    public PageResponse<List<PaymentHistoryDto>>
    getAllWithFilter(@RequestBody FilterDto<PaymentHistoryCriteria> filterDto) {
        return paymentHistoryService.getAllWithFilter(filterDto);
    }
/*
    @GetMapping("/sender-request-id/{senderRequestId}")
    public ResponseEntity<ReceiptDto> getBySenderRequestId(@PathVariable String senderRequestId) {
        return ResponseEntity.ok(paymentHistoryService.getBySenderRequestId(senderRequestId));
    }*/

    @GetMapping("/{id}")
    public PaymentHistoryDto getById(@PathVariable Long id, @RequestParam @Nullable Long merchantId) {
        return paymentHistoryService.getById(id, merchantId);
    }

    @PostMapping("/category-statistics")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(@RequestBody StatisticCriteria statisticCriteria) {
        return ResponseEntity.ok(paymentHistoryService.getStatisticsWithFilterByCategory(statisticCriteria));
    }

    @PostMapping("/category-statistics-by-name")
    public ResponseEntity<Map<LocalDate, BigDecimal>> getCategoryStatisticsByName(@RequestBody StatisticCriteria statisticCriteria) {
        return ResponseEntity.ok(paymentHistoryService.getCategoryStatisticsByCategoryName(statisticCriteria));
    }

    @PostMapping("/service-statistics")
    public ResponseEntity<Map<Integer, BigDecimal>> getServiceStatics(@RequestBody StatisticCriteria statisticCriteria) {
        return ResponseEntity.ok(paymentHistoryService.getStatisticsWithFilterByService(statisticCriteria));
    }

    @PostMapping("/merchant-statistics")
    public ResponseEntity<Map<Long, BigDecimal>> getMerchantStatistics(@RequestBody StatisticCriteria statisticCriteria) {
        return ResponseEntity.ok(paymentHistoryService.getStatisticsWithFilterByMerchant(statisticCriteria));
    }

    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadExcel() throws IOException {
        File file = new File("C:\\Users\\Ali\\Desktop\\PaymentHistory.xlsx");

        if (!file.exists()) {
            throw new IOException("File not found: " + "C:\\Users\\Ali\\Desktop\\PaymentHistory.xlsx");
        }

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(resource);
    }

    @GetMapping("/excel")
    public void generate() {
        paymentHistoryService.generateExcel();
    }
}

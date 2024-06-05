package com.greentechpay.paymenthistoryservice.controller;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("/filter")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>>
    getAllWithFilter(@RequestBody FilterDto<PaymentHistoryCriteria> filterDto) {
        return ResponseEntity.ok(paymentHistoryService.getAllWithFilter(filterDto));
    }

    @GetMapping("/sender-request-id/{senderRequestId}")
    public ResponseEntity<ReceiptDto> getBySenderRequestId(@PathVariable String senderRequestId) {
        return ResponseEntity.ok(paymentHistoryService.getBySenderRequestId(senderRequestId));
    }

    @GetMapping("/receipt-id/{id}")
    public ResponseEntity<ReceiptDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentHistoryService.getById(id));
    }

    @PostMapping("/category-statistics")
    public ResponseEntity<Map<String, BigDecimal>> getCategoryStatistics(@RequestBody StatisticCriteria statisticCriteria) {
        return ResponseEntity.ok(paymentHistoryService.getStatisticsWithFilterByCategory(statisticCriteria));
    }

    @PostMapping("/service-statistics")
    public ResponseEntity<PageResponse<Map<Integer, BigDecimal>>> getServiceStatics(@RequestBody FilterDto<StatisticCriteria> filterDto) {
        return ResponseEntity.ok(paymentHistoryService.getStatisticsWithFilterByService(filterDto));
    }

    /*@GetMapping("/download")
    public void excel(){
        paymentHistoryService.generateExcel();
    }*/
}

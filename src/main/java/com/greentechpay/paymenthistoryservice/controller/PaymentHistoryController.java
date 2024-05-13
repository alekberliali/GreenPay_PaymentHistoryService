package com.greentechpay.paymenthistoryservice.controller;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.service.PaymentHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("/all")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>> getAll(@RequestBody PageRequestDto pageRequestDto) {
        return ResponseEntity.ok(paymentHistoryService.getAll(pageRequestDto));
    }

    @PostMapping("/filter")
    public ResponseEntity<PageResponse<List<PaymentHistoryDto>>> getAllWithFilter(@RequestBody FilterDto filterDto){
        return ResponseEntity.ok(paymentHistoryService.getAllWithFilter(filterDto));
    }
    //TODO BFF services must check validation
    @PostMapping("/page/{userId}")
    public ResponseEntity<PageResponse<Map<LocalDate, List<PaymentHistoryDto>>>>
    getAllWithPageByUserId(@PathVariable String userId, @RequestBody PageRequestDto pageRequestDto) {
        return ResponseEntity.ok(paymentHistoryService.getAllByUserId(userId, pageRequestDto));
    }

    @GetMapping("/sender-request-id/{senderRequestId}")
    public ResponseEntity<ReceiptDto> getBySenderRequestId(@PathVariable String senderRequestId) {
        return ResponseEntity.ok(paymentHistoryService.getBySenderRequestId(senderRequestId));
    }

    @GetMapping("/receipt-id/{id}")
    public ResponseEntity<ReceiptDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(paymentHistoryService.getById(id));
    }
}

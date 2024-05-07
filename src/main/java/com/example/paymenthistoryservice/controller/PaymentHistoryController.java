package com.example.paymenthistoryservice.controller;

import com.example.paymenthistoryservice.dto.PageRequestDto;
import com.example.paymenthistoryservice.dto.PageResponse;
import com.example.paymenthistoryservice.dto.PaymentHistoryDto;
import com.example.paymenthistoryservice.dto.ReceiptDto;
import com.example.paymenthistoryservice.service.PaymentHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment-history")
public class PaymentHistoryController {
    private final PaymentHistoryService paymentHistoryService;

    @PostMapping("/page/{id}")
    public ResponseEntity<PageResponse<Date, List<PaymentHistoryDto>>>
    getAllWithPageByUserId(@PathVariable String id, @Valid @RequestBody PageRequestDto pageRequestDto) {
        return ResponseEntity.ok(paymentHistoryService.getAllByUserId(id, pageRequestDto));
    }

    @GetMapping("/sender-request-id/{senderRequestId}")
    public ResponseEntity<ReceiptDto> getBySenderRequestId(@PathVariable String senderRequestId) {
        return ResponseEntity.ok(paymentHistoryService.getBySenderRequestId(senderRequestId));
    }

    @GetMapping("/receipt-id/{id}")
    public ResponseEntity<ReceiptDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(paymentHistoryService.getById(id));
    }
}

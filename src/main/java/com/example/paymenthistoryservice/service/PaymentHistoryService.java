package com.example.paymenthistoryservice.service;

import com.example.paymenthistoryservice.dto.PageRequestDto;
import com.example.paymenthistoryservice.dto.PageResponse;
import com.example.paymenthistoryservice.dto.PaymentHistoryDto;
import com.example.paymenthistoryservice.dto.ReceiptDto;
import com.example.paymenthistoryservice.entity.PaymentHistory;
import com.example.paymenthistoryservice.entity.TransferType;
import com.example.paymenthistoryservice.exception.ReceiptNotFoundException;
import com.example.paymenthistoryservice.mapper.PaymentHistoryMapper;
import com.example.paymenthistoryservice.repository.PaymentHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.nio.file.ProviderNotFoundException;
import java.sql.Date;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;

    public PageResponse<Date, List<PaymentHistoryDto>> getAllByUserId(String userId, PageRequestDto pageRequestDto) {
        var pageRequest = PageRequest.of(pageRequestDto.page(), pageRequestDto.size());
        var result = paymentHistoryRepository.findAllByUserId(userId, pageRequest);

        Map<Date, List<PaymentHistoryDto>> resultMap = new HashMap<>();
        for (PaymentHistory paymentHistory : result.getContent()) {
            Date date = paymentHistory.getDate();
            List<PaymentHistoryDto> list = resultMap.getOrDefault(date, new ArrayList<>());
            list.add(paymentHistoryMapper.entityToDto(paymentHistory));
            resultMap.put(date, list);
        }

        Map<Date, List<PaymentHistoryDto>> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(resultMap);

        return PageResponse.<Date, List<PaymentHistoryDto>>builder()
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .content(sortedMap)
                .build();
    }

    public ReceiptDto getBySenderRequestId(String senderRequestId) {
        try {
            PaymentHistory paymentHistory = paymentHistoryRepository.findSenderRequestId(senderRequestId);
            return getReceiptDto(paymentHistory);
        } catch (ReceiptNotFoundException e) {
            throw new ReceiptNotFoundException("receipt could not find by sender request id: " + senderRequestId);
        }
    }

    public ReceiptDto getById(Long id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id)
                .orElseThrow(() -> new ProviderNotFoundException("receipt could not find by id: " + id));
        return getReceiptDto(paymentHistory);
    }

    private ReceiptDto getReceiptDto(PaymentHistory paymentHistory) {
        if (paymentHistory.getTransferType().equals(TransferType.Payment)) {
            return ReceiptDto.builder()
                    .amount(paymentHistory.getAmount())
                    .paymentDate(paymentHistory.getPaymentDate())
                    .senderRequestId(paymentHistory.getSenderRequestId())
                    .name(paymentHistory.getServiceName())
                    .from(paymentHistory.getUserId())
                    .to(paymentHistory.getToUser())
                    .currency(paymentHistory.getCurrency())
                    .type(paymentHistory.getTransferType())
                    .status(paymentHistory.getStatus())
                    .build();

        } else {
            return ReceiptDto.builder()
                    .amount(paymentHistory.getAmount())
                    .paymentDate(paymentHistory.getPaymentDate())
                    .senderRequestId(paymentHistory.getSenderRequestId())
                    .name(paymentHistory.getTransferType().name())
                    .from(paymentHistory.getUserId())
                    .to(paymentHistory.getToUser())
                    .currency(paymentHistory.getCurrency())
                    .type(paymentHistory.getTransferType())
                    .status(paymentHistory.getStatus())
                    .build();
        }
    }
}

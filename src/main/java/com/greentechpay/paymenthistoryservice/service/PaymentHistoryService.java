package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import com.greentechpay.paymenthistoryservice.mapper.CriteriaMapper;
import com.greentechpay.paymenthistoryservice.mapper.PaymentHistoryMapper;
import com.greentechpay.paymenthistoryservice.repository.PaymentHistoryRepository;
import com.greentechpay.paymenthistoryservice.service.specification.PaymentHistorySpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final CriteriaMapper criteriaMapper;

    public PageResponse<List<PaymentHistoryDto>> getAll(PageRequestDto pageRequestDto) {
        var pageRequest = PageRequest.of(pageRequestDto.page(), pageRequestDto.size());
        var result = paymentHistoryRepository.findAllBy(pageRequest);

        return PageResponse.<List<PaymentHistoryDto>>builder()
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .content(result.getContent().stream()
                        .map(paymentHistoryMapper::entityToDto)
                        .toList())
                .build();
    }

    public PageResponse<List<PaymentHistoryDto>> getAllWithFilter(FilterDto filterDto) {
        var pageRequest = PageRequest.of(filterDto.getPageRequestDto().page(), filterDto.getPageRequestDto().size());
        if (filterDto.getUserId().isBlank() || paymentHistoryRepository.existsByUserId(filterDto.getUserId())) {
            var result = paymentHistoryRepository
                    .findAll(new PaymentHistorySpecification(criteriaMapper.filterToCriteria(filterDto)), pageRequest);

            return PageResponse.<List<PaymentHistoryDto>>builder()
                    .totalPages(result.getTotalPages())
                    .totalElements(result.getTotalElements())
                    .content(result.getContent().stream()
                            .map(paymentHistoryMapper::entityToDto)
                            .toList())
                    .build();
        } else throw new RuntimeException("user could not find by id: " + filterDto.getUserId());
    }

    public PageResponse<Map<LocalDate, List<PaymentHistoryDto>>> getAllByUserId(String userId, PageRequestDto pageRequestDto) {
        if (paymentHistoryRepository.existsByUserId(userId)) {
            var pageRequest = PageRequest.of(pageRequestDto.page(), pageRequestDto.size());
            var result = paymentHistoryRepository.findAllByUserId(userId, pageRequest);

            Map<LocalDate, List<PaymentHistoryDto>> resultMap = new HashMap<>();
            for (PaymentHistory paymentHistory : result.getContent()) {
                LocalDate date = paymentHistory.getDate();
                List<PaymentHistoryDto> list = resultMap.getOrDefault(date, new ArrayList<>());
                list.add(paymentHistoryMapper.entityToDto(paymentHistory));
                resultMap.put(date, list);
            }

            Map<LocalDate, List<PaymentHistoryDto>> sortedMap = new TreeMap<>(Collections.reverseOrder());
            sortedMap.putAll(resultMap);

            return PageResponse.<Map<LocalDate, List<PaymentHistoryDto>>>builder()
                    .totalElements(result.getTotalElements())
                    .totalPages(result.getTotalPages())
                    .content(sortedMap)
                    .build();
        } else throw new RuntimeException("user could not find by id: " + userId);
    }

    public ReceiptDto getBySenderRequestId(String senderRequestId) {
        try {
            PaymentHistory paymentHistory = paymentHistoryRepository.findSenderRequestId(senderRequestId);
            return getReceiptDto(paymentHistory);
        } catch (RuntimeException e) {
            throw new RuntimeException("receipt could not find by sender request id: " + senderRequestId);
        }
    }

    public ReceiptDto getById(Long id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("receipt could not find by id: " + id));
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

    @KafkaListener(topics = "Transaction-Message-Create", groupId = "1")
    private void create(TransactionDto transactionDto) {
        paymentHistoryRepository.save(paymentHistoryMapper.dtoToEntity(transactionDto));
    }

    @KafkaListener(topics = "Transaction-Message-Update", groupId = "1")
    private void update(PaymentStatusUpdate paymentStatusUpdate) {
        if (paymentHistoryRepository.existsByTransactionId(paymentStatusUpdate.getTransactionId())) {
            var paymentHistory = paymentHistoryRepository.findByTransactionId(paymentStatusUpdate.getTransactionId());
            paymentHistory.setStatus(paymentStatusUpdate.getStatus());
            paymentHistoryRepository.save(paymentHistory);
        } else
            throw new RuntimeException("Transaction could not find by id: " + paymentStatusUpdate.getTransactionId());
    }
}

package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import com.greentechpay.paymenthistoryservice.mapper.PaymentHistoryMapper;
import com.greentechpay.paymenthistoryservice.repository.PaymentHistoryRepository;
import com.greentechpay.paymenthistoryservice.service.specification.PaymentHistorySpecification;
import com.greentechpay.paymenthistoryservice.service.specification.StatisticSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {
    private final PaymentHistoryRepository paymentHistoryRepository;
    private final PaymentHistoryMapper paymentHistoryMapper;
    private final ExcelFileService excelFileService;

    @Scheduled(cron = "0 0 4 * * ?")
    public void generateExcel() {
        var paymentHistoryList = getAll();
        try {
            excelFileService.dataToExcel(paymentHistoryList);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    protected List<PaymentHistory> getAll() {
        return paymentHistoryRepository.findAllByDate(LocalDate.now().minusDays(1));
    }

    public PageResponse<List<PaymentHistoryDto>> getAllWithFilter(FilterDto<PaymentHistoryCriteria> filterDto) {
        var pageRequest = PageRequest.of(filterDto.getPageRequestDto().page(), filterDto.getPageRequestDto().size());
        var result = paymentHistoryRepository
                .findAll(new PaymentHistorySpecification(filterDto.getData()), pageRequest);

        return PageResponse.<List<PaymentHistoryDto>>builder()
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .content(result.getContent().stream()
                        .map(paymentHistoryMapper::entityToDto)
                        .toList())
                .build();
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
        System.out.println(paymentHistory.getTransferType());
        return getReceiptDto(paymentHistory);
    }

    private ReceiptDto getReceiptDto(PaymentHistory paymentHistory) {
        if (paymentHistory.getTransferType().equals(TransferType.BillingPayment)) {
            return ReceiptDto.builder()
                    .amount(paymentHistory.getAmount())
                    .paymentDate(paymentHistory.getPaymentDate())
                    .senderRequestId(paymentHistory.getSenderRequestId())
                    .serviceId(paymentHistory.getServiceId())
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
                    .from(paymentHistory.getUserId())
                    .to(paymentHistory.getToUser())
                    .currency(paymentHistory.getCurrency())
                    .type(paymentHistory.getTransferType())
                    .status(paymentHistory.getStatus())
                    .build();
        }
    }

    public Map<String, BigDecimal> getStatisticsWithFilterByCategory(StatisticCriteria statisticCriteria) {
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository
                .findAll(new StatisticSpecification(statisticCriteria));

        Map<String, BigDecimal> categoryPayments = new HashMap<>();

        for (PaymentHistory ph : paymentHistoryList) {
            BigDecimal amount = ph.getAmount();
            String categoryName = ph.getCategoryName();

            if (!categoryPayments.containsKey(categoryName)) {
                categoryPayments.put(categoryName, amount);
            } else {
                BigDecimal total = categoryPayments.get(categoryName);
                total = total.add(ph.getAmount());
                categoryPayments.put(categoryName, total);
            }
        }
        return categoryPayments;
    }

    public PageResponse<Map<Integer, BigDecimal>> getStatisticsWithFilterByService(FilterDto<StatisticCriteria> filterDto) {
        var pageRequest = PageRequest.of(filterDto.getPageRequestDto().page(), filterDto.getPageRequestDto().size());
        var paymentHistoryList = paymentHistoryRepository
                .findAll(new StatisticSpecification(filterDto.getData()), pageRequest);

        Map<Integer, BigDecimal> servicePayments = new HashMap<>();

        for (PaymentHistory ph : paymentHistoryList) {
            BigDecimal amount = ph.getAmount();
            Integer serviceId = ph.getServiceId();

            if (!servicePayments.containsKey(serviceId)) {
                servicePayments.put(serviceId, amount);
            } else {
                BigDecimal total = servicePayments.get(serviceId);
                total = total.add(ph.getAmount());
                servicePayments.put(serviceId, total);
            }
        }
        return PageResponse.<Map<Integer, BigDecimal>>builder()
                .totalPages(paymentHistoryList.getTotalPages())
                .totalElements(paymentHistoryList.getTotalElements())
                .content(servicePayments)
                .build();
    }

    @KafkaListener(topics = "payment-success-saga")
    private void create(PaymentSuccessEvent<TResponse> transactionDto) {
        System.out.println(transactionDto.getResponse().getAmount());

        paymentHistoryRepository.save(paymentHistoryMapper.dtoToEntity(transactionDto.getResponse()));
    }

    //@KafkaListener(topics = "Transaction-Message-Update", groupId = "1")
    private void update(PaymentStatusUpdate paymentStatusUpdate) {
        var paymentHistory = paymentHistoryRepository.findByTransactionId(paymentStatusUpdate.getTransactionId());
        paymentHistory.setStatus(paymentStatusUpdate.getStatus());
        paymentHistory.setUpdateDate(LocalDateTime.now());
        paymentHistoryRepository.save(paymentHistory);
    }
}

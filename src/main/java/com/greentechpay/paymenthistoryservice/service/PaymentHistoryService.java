package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.*;
import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import com.greentechpay.paymenthistoryservice.kafka.dto.*;
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
    private final NotificationSendService notificationSendService;

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
/*
    public ReceiptDto getBySenderRequestId(String senderRequestId) {
        try {
            PaymentHistory paymentHistory = paymentHistoryRepository.findSenderRequestId(senderRequestId);
            return getReceiptDto(paymentHistory);
        } catch (RuntimeException e) {
            throw new RuntimeException("receipt could not find by sender request id: " + senderRequestId);
        }
    }*/

    public PaymentHistoryDto getById(Long id) {
        PaymentHistory paymentHistory = paymentHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("receipt could not find by id: " + id));
        return paymentHistoryMapper.entityToDto(paymentHistory);
    }
/*
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
    }*/

    public Map<LocalDate, BigDecimal> getCategoryStatisticsByCategoryName(StatisticCriteria statisticCriteria) {
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository
                .findAll(new StatisticSpecification(statisticCriteria));
        Map<LocalDate, BigDecimal> categoryStatistics = new HashMap<>();
        for (PaymentHistory ph : paymentHistoryList) {
            BigDecimal amount = ph.getAmount();
            LocalDate date = ph.getDate();

            if (!categoryStatistics.containsKey(date)) {
                categoryStatistics.put(date, amount);
            } else {
                BigDecimal total = categoryStatistics.get(date);
                total = total.add(ph.getAmount());
                categoryStatistics.put(date, total);
            }
        }
        return categoryStatistics;
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

    public Map<Long, BigDecimal> getStatisticsWithFilterByMerchant(StatisticCriteria statisticCriteria) {
        List<PaymentHistory> paymentHistoryList = paymentHistoryRepository
                .findAll(new StatisticSpecification(statisticCriteria));
        Map<Long, BigDecimal> merchantPayments = new HashMap<>();

        for (PaymentHistory ph : paymentHistoryList) {
            BigDecimal amount = ph.getAmount();
            Long merchantId = ph.getMerchantId();

            if (!merchantPayments.containsKey(merchantId)) {
                merchantPayments.put(merchantId, amount);
            } else {
                BigDecimal total = merchantPayments.get(merchantId);
                total = total.add(ph.getAmount());
                merchantPayments.put(merchantId, total);
            }
        }
        return merchantPayments;
    }

    public Map<Integer, BigDecimal> getStatisticsWithFilterByService(StatisticCriteria statisticCriteria) {
        var paymentHistoryList = paymentHistoryRepository
                .findAll(new StatisticSpecification(statisticCriteria));

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
        return servicePayments;
    }

    //TODO amount and amountOut (medaxil,mexaric)
    @KafkaListener(topics = "payment-create-saga", containerFactory = "kafkaListenerContainerFactory")
    protected void createBillingPayment(PaymentSuccessEvent<TResponse> transactionDto) {
        var paymentHistory = paymentHistoryMapper.dtoToEntity(transactionDto.getResponse());
        paymentHistory.setPaymentDate(transactionDto.getResponse().getPaymentDate().toLocalDateTime());
        paymentHistory.setDate(transactionDto.getResponse().getPaymentDate().toLocalDateTime().toLocalDate());
        paymentHistoryRepository.save(paymentHistory);
        notificationSendService.sendPaymentCreateNotification(paymentHistory);
    }

    @KafkaListener(topics = "payment-update-saga", containerFactory = "kafkaUpdateListenerContainerFactory")
    protected void updateBullingPayment(UpdateBalanceToBalance updateBalanceToBalance) {
        var paymentHistory = paymentHistoryRepository.findByTransactionId(updateBalanceToBalance.getTransactionId());
        notificationSendService.sendPaymentUpdateNotification(paymentHistory, updateBalanceToBalance);
        paymentHistory.setStatus(updateBalanceToBalance.getStatus());
        paymentHistory.setUpdateDate(LocalDateTime.now());
        paymentHistoryRepository.save(paymentHistory);
    }

    @KafkaListener(topics = "balanceToBalance-payment-history-created-message",
            containerFactory = "kafkaListenerContainerFactoryBalanceToBalance")
    private void createBalanceToBalance(CreateBalanceToBalance transactionDto) {
        var paymentHistory = paymentHistoryMapper.balanceToBalanceToEntity(transactionDto);
        paymentHistory.setPaymentDate(transactionDto.getPaymentDate().toLocalDateTime());
        paymentHistory.setDate(transactionDto.getPaymentDate().toLocalDateTime().toLocalDate());
        paymentHistory.setCategoryName("Transfer");
        paymentHistoryRepository.save(paymentHistory);
        notificationSendService.sendPaymentCreateNotification(paymentHistory);
    }

    @KafkaListener(topics = "balanceToBalance-payment-history-updated-message",
            containerFactory = "kafkaUpdateListenerContainerFactory")
    protected void updateBalanceToBalance(UpdateBalanceToBalance updateBalanceToBalance) {
        var paymentHistory = paymentHistoryRepository.findByTransactionId(updateBalanceToBalance.getTransactionId());
        paymentHistory.setAmountOut(updateBalanceToBalance.getAmountOut());
        paymentHistory.setCurrencyOut(updateBalanceToBalance.getCurrencyOut());
        paymentHistory.setUpdateDate(LocalDateTime.now());
        paymentHistory.setStatus(updateBalanceToBalance.getStatus());
        paymentHistory.setReceiverIban(updateBalanceToBalance.getReceiverIban());
        paymentHistoryRepository.save(paymentHistory);
    }

    @KafkaListener(topics = "card-to-balance-created-payment-history-message",
            containerFactory = "kafkaListenerContainerFactoryCardToBalance")
    protected void createCardToBalance(CreateCardToBalance createCardToBalance) {
        var paymentHistory = paymentHistoryMapper.cardToBalance(createCardToBalance);
        paymentHistory.setCurrencyOut(Currency.NONE);
        paymentHistory.setPaymentDate(createCardToBalance.getPaymentDate().toLocalDateTime());
        paymentHistory.setDate(createCardToBalance.getPaymentDate().toLocalDateTime().toLocalDate());
        paymentHistory.setCategoryName("Transfer");
        paymentHistoryRepository.save(paymentHistory);
    }

    @KafkaListener(topics = "card-to-balance-update-payment-history-message",
            containerFactory = "kafkaListenerContainerFactoryUpdateCardToBalance")
    protected void updateCardToBalance(UpdateCardToBalance updateCardToBalance) {
        var paymentHistory = paymentHistoryRepository.findByTransactionId(updateCardToBalance.getTransactionId());
        paymentHistory.setAmount(updateCardToBalance.getAmount());
        paymentHistory.setStatus(updateCardToBalance.getStatus());
        paymentHistory.setCurrency(updateCardToBalance.getCurrency());
        paymentHistory.setRequestField(updateCardToBalance.getRequestField());
        paymentHistory.setUpdateDate(updateCardToBalance.getUpdateDate().toLocalDateTime());
        paymentHistory.setExternalPaymentId(updateCardToBalance.getExternalPaymentId());
        paymentHistoryRepository.save(paymentHistory);
        notificationSendService.sendPaymentCreateNotification(paymentHistory);
    }

    @KafkaListener(topics = "insert-payment-history-balance-to-card",
            containerFactory = "kafkaListenerContainerFactoryBalanceToCard")
    protected void createBalanceToCard(CreateBalanceToCard createBalanceToCard) {
        var paymentHistory = paymentHistoryMapper.balanceToCard(createBalanceToCard);
        paymentHistory.setCategoryName("Transfer");
        paymentHistory.setPaymentDate(createBalanceToCard.getPaymentDate().toLocalDateTime());
        paymentHistory.setDate(createBalanceToCard.getPaymentDate().toLocalDateTime().toLocalDate());
        paymentHistory.setCurrencyOut(Currency.NONE);
        paymentHistoryRepository.save(paymentHistory);
        notificationSendService.sendPaymentCreateNotification(paymentHistory);
    }

    @KafkaListener(topics = "update-payment-history-balance-to-card",
            containerFactory = "kafkaListenerContainerFactoryUpdateBalanceToCard")
    protected void updateBalanceToCard(UpdateBalanceToCard updateBalanceToCard) {
        var paymentHistory = paymentHistoryRepository.findByTransactionId(updateBalanceToCard.getTransactionId());
        paymentHistory.setStatus(updateBalanceToCard.getStatus());
        paymentHistory.setUpdateDate(updateBalanceToCard.getUpdateDate().toLocalDateTime());
        paymentHistory.setExternalPaymentId(updateBalanceToCard.getExternalPaymentId());
        paymentHistoryRepository.save(paymentHistory);
    }
}

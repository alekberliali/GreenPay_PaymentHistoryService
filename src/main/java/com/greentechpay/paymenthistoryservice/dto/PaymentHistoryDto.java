package com.greentechpay.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentHistoryDto {
    private Long id;
    private BigDecimal amount;
    private String userId;
    private Integer vendorId;
    private String toUser;
    private String requestField;
    private String externalPaymentId;
    private Integer serviceId;
    private Long merchantId;
    private String senderRequestId;
    private String senderIban;
    private String receiverIban;
    private TransferType transferType;
    private LocalDateTime updateDate;
    private LocalDateTime paymentDate;
    private String transactionId;
    private String categoryName;
    private Currency currency;
    private Status status;
}

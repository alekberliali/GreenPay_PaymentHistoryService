package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentHistoryDto {
    private Long id;
    private BigDecimal amount;
    private String userId;
    private Integer vendorId;
    private String toUser;
    private String requestField;
    private Integer serviceId;
    private Integer merchantId;
    private String senderRequestId;
    private String senderIban;
    private String receiverIban;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private String transactionId;
    private Status status;
}

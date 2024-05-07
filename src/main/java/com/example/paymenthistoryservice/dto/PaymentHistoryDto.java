package com.example.paymenthistoryservice.dto;

import com.example.paymenthistoryservice.entity.Status;
import com.example.paymenthistoryservice.entity.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentHistoryDto {
    private Long id;
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private String senderRequestId;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private String transactionId;
    private Status status;
}

package com.example.paymenthistoryservice.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class PaymentHistoryDto {
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private String transferType;
    private Timestamp paymentDate;
    private Boolean paymentStatus;
}

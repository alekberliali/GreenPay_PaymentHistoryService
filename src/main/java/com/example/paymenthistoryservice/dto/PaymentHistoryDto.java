package com.example.paymenthistoryservice.dto;

import com.example.paymenthistoryservice.entity.Status;
import com.example.paymenthistoryservice.entity.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentHistoryDto {
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private TransferType transferType;
    private LocalDateTime paymentDate;
    private Status status;
}

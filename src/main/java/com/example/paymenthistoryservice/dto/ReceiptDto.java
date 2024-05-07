package com.example.paymenthistoryservice.dto;


import com.example.paymenthistoryservice.entity.Status;
import com.example.paymenthistoryservice.entity.TransferType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReceiptDto {
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private String senderRequestId;
    private String name;
    private String from;
    private String to;
    private String currency;
    private TransferType type;
    private Status status;
}

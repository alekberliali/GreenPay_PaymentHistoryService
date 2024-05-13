package com.greentechpay.paymenthistoryservice.dto;


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
    private Currency currency;
    private TransferType type;
    private Status status;
}

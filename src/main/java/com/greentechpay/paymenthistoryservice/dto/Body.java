package com.greentechpay.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class Body {
    private BigDecimal amount;
    private Currency currency;
    private String requestField;
    private LocalDateTime date;
    private String description;
}

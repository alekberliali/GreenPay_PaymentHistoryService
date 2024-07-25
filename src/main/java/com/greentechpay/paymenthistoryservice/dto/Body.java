package com.greentechpay.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
@Data
@Builder
public class Body {
    private BigDecimal amount;
    private Currency currency;
    private String date;
    private String description;
}

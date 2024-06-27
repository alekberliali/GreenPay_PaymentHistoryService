package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBalanceToBalance {
    private BigDecimal amount;
    private String transactionId;
    private Timestamp paymentDate;
    private Integer merchantId;
    private Status status;
    private String senderRequestId;
    private String currency;
    private String currencyOut;
    private BigDecimal amountOut;
    private String requestField;
    private String senderIban;
    private String senderUserId;
    private String receiverUserId;
    private String receiverIban;

}

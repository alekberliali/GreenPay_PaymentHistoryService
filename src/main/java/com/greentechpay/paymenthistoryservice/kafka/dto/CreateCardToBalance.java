package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class CreateCardToBalance {
    private BigDecimal amount;
    private String transactionId;
    private Long merchantId;
    private Timestamp paymentDate;
    private Status status;
    private String senderRequestId;
    private Currency currency;
    private String receiverIban;
    private TransferType transferType;
    private Integer vendorId;
    private String userId;
}

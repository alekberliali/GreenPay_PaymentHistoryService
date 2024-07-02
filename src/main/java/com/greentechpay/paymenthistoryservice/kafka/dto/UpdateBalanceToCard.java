package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateBalanceToCard {
    private String transactionId;
    private Status status;
    private Timestamp updateDate;
    private String externalPaymentId;
}

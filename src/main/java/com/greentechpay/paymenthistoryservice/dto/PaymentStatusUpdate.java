package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

@Data
public class PaymentStatusUpdate {
    private String transactionId;
    private Status status;
}

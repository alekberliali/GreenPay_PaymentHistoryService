package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

@Data
public class PaymentSuccessEvent<T> {
    private String correlationId;
    private T response;
}

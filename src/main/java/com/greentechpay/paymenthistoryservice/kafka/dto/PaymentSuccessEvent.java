package com.greentechpay.paymenthistoryservice.kafka.dto;
import lombok.Data;

@Data
public class PaymentSuccessEvent<T> {
    private String correlationId;
    private T response;
}

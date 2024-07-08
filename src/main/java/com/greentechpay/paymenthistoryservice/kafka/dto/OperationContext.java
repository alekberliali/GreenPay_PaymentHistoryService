package com.greentechpay.paymenthistoryservice.kafka.dto;


import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

@Data
public class OperationContext {
    private String correlationId;
    private Status paymentStatus;
}

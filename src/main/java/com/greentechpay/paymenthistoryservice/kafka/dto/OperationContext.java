package com.greentechpay.paymenthistoryservice.kafka.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.greentechpay.paymenthistoryservice.dto.Status;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperationContext {
    private String correlationId;
    private Status paymentStatus;
}

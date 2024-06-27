package com.greentechpay.paymenthistoryservice.kafka.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.greentechpay.paymenthistoryservice.dto.Body;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentNotificationMessageEvent {
    private String UserId;
    private String ReceiverUserId;
    private Body ReceiverBody;
    private String Title;
    private Body Body;
    private String Image;
    private Map<String, String> Data;
}

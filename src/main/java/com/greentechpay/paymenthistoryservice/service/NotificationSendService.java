package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.Body;
import com.greentechpay.paymenthistoryservice.dto.PaymentNotificationMessageEvent;
import com.greentechpay.paymenthistoryservice.dto.PaymentUpdateEvent;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSendService {
    private final KafkaTemplate<String, PaymentNotificationMessageEvent> kafkaTemplate;

    protected void sendPaymentCreateNotification(PaymentHistory paymentHistory) {
        var body = Body.builder()
                .amount(paymentHistory.getAmount())
                .currency(paymentHistory.getCurrency().toString())
                .date(paymentHistory.getPaymentDate())
                .build();
        PaymentNotificationMessageEvent message;
        if (paymentHistory.getToUser() != null) {
            message = PaymentNotificationMessageEvent.builder()
                    .Title(paymentHistory.getTransferType().toString())
                    .UserId(paymentHistory.getUserId())
                    .Body(body)
                    .ReceiverUserId(paymentHistory.getToUser())
                    .ReceiverBody(body)
                    .build();
        } else {
            message = PaymentNotificationMessageEvent.builder()
                    .Title(paymentHistory.getTransferType().toString())
                    .UserId(paymentHistory.getUserId())
                    .Body(body)
                    .build();
        }
        kafkaTemplate.send("Notification-Message", message);
    }

    protected void sendPaymentUpdateNotification(PaymentHistory paymentHistory, PaymentUpdateEvent paymentUpdateEvent) {
        var body = Body.builder()
                .description("Your payments status changed from " + paymentHistory.getStatus()
                        + " to " + paymentUpdateEvent.getStatus())
                .build();
        var message = PaymentNotificationMessageEvent.builder()
                .Body(body)
                .build();
        kafkaTemplate.send("Notification-Message", message);
    }
}

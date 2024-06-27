package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.Body;
import com.greentechpay.paymenthistoryservice.kafka.dto.PaymentNotificationMessageEvent;
import com.greentechpay.paymenthistoryservice.kafka.dto.PaymentUpdateEvent;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationSendService {
    private final KafkaTemplate<String, PaymentNotificationMessageEvent> kafkaTemplate;

    protected void sendPaymentCreateNotification(PaymentHistory paymentHistory) {
        var senderBody = Body.builder()
                .amount(paymentHistory.getAmount())
                .currency(paymentHistory.getCurrency())
                .date(paymentHistory.getPaymentDate())
                .requestField(paymentHistory.getRequestField())
                .build();
        var receiverBody = Body.builder()
                .amount(paymentHistory.getAmountOut())
                .currency(paymentHistory.getCurrencyOut())
                .date(paymentHistory.getPaymentDate())
                .build();
        PaymentNotificationMessageEvent message;
        if (paymentHistory.getTransferType().equals(TransferType.BalanceToBalance)) {
            message = PaymentNotificationMessageEvent.builder()
                    .Title(paymentHistory.getTransferType().toString())
                    .UserId(paymentHistory.getUserId())
                    .Body(senderBody)
                    .ReceiverUserId(paymentHistory.getToUser())
                    .ReceiverBody(receiverBody)
                    .build();
        } else {
            message = PaymentNotificationMessageEvent.builder()
                    .Title(paymentHistory.getTransferType().toString())
                    .UserId(paymentHistory.getUserId())
                    .Body(senderBody)
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

package com.greentechpay.paymenthistoryservice.service;

import com.greentechpay.paymenthistoryservice.dto.Body;
import com.greentechpay.paymenthistoryservice.kafka.dto.*;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class NotificationSendService {
    private final KafkaTemplate<String, PaymentNotificationMessageEvent> kafkaTemplate;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

    protected void sendPaymentCreateNotification(PaymentHistory paymentHistory) {
        var senderBody = Body.builder()
                .amount(paymentHistory.getAmount())
                .currency(paymentHistory.getCurrency())
                .date(paymentHistory.getPaymentDate().format(formatter))
                .description(paymentHistory.getStatus().toString())
                .build();
        var receiverBody = Body.builder()
                .amount(paymentHistory.getAmountOut())
                .currency(paymentHistory.getCurrencyOut())
                .date(paymentHistory.getPaymentDate().format(formatter))
                .description(paymentHistory.getStatus().toString())
                .build();
        PaymentNotificationMessageEvent message;
        if (paymentHistory.getTransferType().equals(TransferType.IbanToIban) ||
                paymentHistory.getTransferType().equals(TransferType.IbanToPhoneNumber)) {
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

    protected void sendPaymentUpdateNotification(PaymentHistory paymentHistory, OperationContext operationContext) {
        var body = Body.builder()
                .description(paymentHistory.getAmount() + " Your payments status changed from " + paymentHistory.getStatus()
                        + " to " + operationContext.getPaymentStatus())
                .build();
        var message = PaymentNotificationMessageEvent.builder()
                .Title("Payment status update")
                .Body(body)
                .build();
        kafkaTemplate.send("Notification-Message", message);
    }

    protected void sendUpdateBalanceToCard(PaymentHistory paymentHistory, UpdateBalanceToCard updateBalanceToCard) {
        var body = Body.builder()
                .description(paymentHistory.getAmount() + " Your transfer status changed from " + paymentHistory.getStatus()
                        + " to " + updateBalanceToCard.getStatus())
                .build();
        var message = PaymentNotificationMessageEvent.builder()
                .Title("Balance to card status update")
                .Body(body)
                .build();
        kafkaTemplate.send("Notification-Message", message);
    }

    protected void sendUpdateCardToBalance(PaymentHistory paymentHistory, UpdateCardToBalance updateCardToBalance){
        var body = Body.builder()
                .description(paymentHistory.getAmount() + " Your transfer status changed from " + paymentHistory.getStatus()
                        + " to " + updateCardToBalance.getStatus())
                .build();
        var message = PaymentNotificationMessageEvent.builder()
                .Title("Card to balance status update")
                .Body(body)
                .build();
        kafkaTemplate.send("Notification-Message", message);
    }
}

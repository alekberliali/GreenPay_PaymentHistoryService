package com.greentechpay.paymenthistoryservice.dto;

public enum TransferType {
    BalanceToBalance,
    BalanceToCard,
    CardToBalance,
    BillingPayment,
    Qr,
    Nfc,
}

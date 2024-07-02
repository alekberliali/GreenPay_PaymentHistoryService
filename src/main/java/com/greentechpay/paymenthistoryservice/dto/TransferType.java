package com.greentechpay.paymenthistoryservice.dto;

public enum TransferType {
    IbanToPhoneNumber,
    IbanToIban,
    BalanceToCard,
    CardToBalance,
    BillingPayment,
    Qr,
    Nfc,
}

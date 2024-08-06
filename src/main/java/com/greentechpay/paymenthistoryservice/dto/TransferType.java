package com.greentechpay.paymenthistoryservice.dto;

public enum TransferType {
    IbanToPhoneNumber,
    IbanToIban,
    UIdToUId,
    UIdToIban,
    IbanToUId,
    BalanceToCard,
    CardToBalance,
    BillingPayment,
    Qr,
    Nfc,
}

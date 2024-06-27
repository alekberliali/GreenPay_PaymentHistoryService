package com.greentechpay.paymenthistoryservice.dto;


public enum Status {
    Created,
    Fail,
    Success,
    Pending,
    TransactinCreated,
    SendingToVendor,
    CreatedAtVendor,
    RequestBeingProcessed,
    TransactionProgress,
    TransactionSuccessfully,
    AuthorisationError,
    InsufficientFunds,
    TransactionCanceled,
    TransactionNotFound,
    IncorrectAccountFormat,
    AccountNotFound,
    OperatorProhibition,
    AnonymousWalletProhibited,
    TechnicalError,
    AccountInactive,
    InvalidAmountRange,
    AmountTooSmall,
    AmountTooLarge,
    InvoiceCheckFailed,
    UnknownOperatorError,
    RequestFailed,
    ServiceRouteNotFound,
    NoExchangeRate,
    DatabaseWriteError,
    InvalidStatus
}

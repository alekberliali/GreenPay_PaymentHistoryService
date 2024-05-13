package com.greentechpay.paymenthistoryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PaymentHistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PaymentHistoryServiceApplication.class, args);
    }

}

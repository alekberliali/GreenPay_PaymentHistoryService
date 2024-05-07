package com.example.paymenthistoryservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(region = "payment_history", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "payment_history", schema = "public")
public class PaymentHistory {
    @Id
    private Long id;
    private BigDecimal amount;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "try_count")
    private Integer tryCount;
    @Column(name = "external_payment_id")
    private String externalPaymentId;
    @Column(name = "sender_request_id")
    private String senderRequestId;
    private String currency;
    @Column(name = "currency_out")
    private String currencyOut;
    @Column(name = "amount_out")
    private String amountOut;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "to_user")
    private String toUser;
    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferType transferType;
    @Column(name = "service_id")
    private String serviceName;
    @Column(name = "category_id")
    private Integer categoryId;
    private Date date;
}

package com.greentechpay.paymenthistoryservice.entity;

import com.greentechpay.paymenthistoryservice.dto.Currency;
import com.greentechpay.paymenthistoryservice.dto.Status;
import com.greentechpay.paymenthistoryservice.dto.TransferType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(region = "payment_history", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "payment_history", schema = "public")
public class PaymentHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    @Column(name = "transaction_id")
    private String transactionId;
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "update_date")
    private LocalDateTime updateDate;
    @Column(name = "try_count")
    private Integer tryCount;
    @Column(name = "external_payment_id")
    private String externalPaymentId;
    @Column(name = "sender_request_id")
    private String senderRequestId;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    @Column(name = "currency_out")
    private Currency currencyOut;
    @Column(name = "amount_out")
    private BigDecimal amountOut;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "to_user")
    private String toUser;
    @Column(name = "vendor_id")
    private Integer vendorId;
    @Column(name = "request_field")
    private String requestField;
    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_type")
    private TransferType transferType;
    @Column(name = "service_id")
    private Integer serviceId;
    @Column(name = "merchant_id")
    private Long merchantId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "sender_iban")
    private String senderIban;
    @Column(name = "receiver_iban")
    private String receiverIban;
    private LocalDate date;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PaymentHistory that = (PaymentHistory) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}

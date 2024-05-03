package com.example.paymenthistoryservice.entity;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(region = "payment_history", usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "payment_history", schema = "public")
public class PaymentHistory {
    @Id
    private Integer id;
    private String userId;
    private BigDecimal amount;
    private String toUser;
    private String serviceName;
    private String transferType;
    private Timestamp paymentDate;
    private Date date;
    private Boolean paymentStatus;
}

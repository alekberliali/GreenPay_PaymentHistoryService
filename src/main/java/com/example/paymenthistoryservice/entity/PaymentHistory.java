package com.example.paymenthistoryservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

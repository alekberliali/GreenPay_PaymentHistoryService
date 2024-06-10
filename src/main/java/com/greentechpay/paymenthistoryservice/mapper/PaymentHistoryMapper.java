package com.greentechpay.paymenthistoryservice.mapper;

import com.greentechpay.paymenthistoryservice.dto.PaymentHistoryDto;
import com.greentechpay.paymenthistoryservice.dto.TResponse;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    PaymentHistoryDto entityToDto(PaymentHistory paymentHistory);

    PaymentHistory dtoToEntity(TResponse TResponse);
}

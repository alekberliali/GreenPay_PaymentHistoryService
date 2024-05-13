package com.greentechpay.paymenthistoryservice.mapper;

import com.greentechpay.paymenthistoryservice.dto.FilterDto;
import com.greentechpay.paymenthistoryservice.dto.PaymentHistoryCriteria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")

public interface CriteriaMapper {
    PaymentHistoryCriteria filterToCriteria(FilterDto filterDto);
}

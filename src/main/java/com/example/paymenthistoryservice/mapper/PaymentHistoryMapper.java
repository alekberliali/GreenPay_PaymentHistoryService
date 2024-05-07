package com.example.paymenthistoryservice.mapper;

import com.example.paymenthistoryservice.dto.PaymentHistoryDto;
import com.example.paymenthistoryservice.dto.ReceiptDto;
import com.example.paymenthistoryservice.entity.PaymentHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    PaymentHistoryDto entityToDto (PaymentHistory paymentHistory);

    ReceiptDto HistoryToReceiptDto(PaymentHistory paymentHistory);
}

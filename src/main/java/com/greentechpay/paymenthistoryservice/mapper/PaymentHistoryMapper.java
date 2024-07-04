package com.greentechpay.paymenthistoryservice.mapper;

import com.greentechpay.paymenthistoryservice.dto.PaymentHistoryDto;
import com.greentechpay.paymenthistoryservice.kafka.dto.CreateBalanceToBalance;
import com.greentechpay.paymenthistoryservice.kafka.dto.CreateBalanceToCard;
import com.greentechpay.paymenthistoryservice.kafka.dto.CreateCardToBalance;
import com.greentechpay.paymenthistoryservice.kafka.dto.TResponse;
import com.greentechpay.paymenthistoryservice.entity.PaymentHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {
    PaymentHistoryDto entityToDto(PaymentHistory paymentHistory);

    PaymentHistory dtoToEntity(TResponse TResponse);

    @Mapping(target = "toUser", source = "receiverUserId")
    @Mapping(target = "userId", source = "senderUserId")
    PaymentHistory balanceToBalanceToEntity(CreateBalanceToBalance balanceToBalance);

    @Mapping(target = "paymentDate", ignore = true)
   /* @Mapping(target = "currency", ignore = true)
    @Mapping(target = "transferType", ignore = true)*/
    PaymentHistory cardToBalance(CreateCardToBalance createCardToBalance);

    @Mapping(target = "paymentDate", ignore = true)
    PaymentHistory balanceToCard(CreateBalanceToCard createBalanceToCard);
}

package com.greentechpay.paymenthistoryservice.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FilterDto<T> {
    private PageRequestDto pageRequestDto;
    private T data;
}

package com.greentechpay.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PageResponse<T> {
    private Long totalElements;
    private Integer totalPages;
    private T content;
}

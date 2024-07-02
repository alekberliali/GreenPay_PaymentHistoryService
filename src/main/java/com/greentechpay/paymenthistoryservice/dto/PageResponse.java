package com.greentechpay.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PageResponse<T> {
    private Long totalElements;
    private Integer totalPages;
    private T content;
}

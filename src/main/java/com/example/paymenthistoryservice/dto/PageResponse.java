package com.example.paymenthistoryservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class PageResponse<K,V> {
    private Long totalElements;
    private Integer totalPages;
    private Map<K,V> content;
}

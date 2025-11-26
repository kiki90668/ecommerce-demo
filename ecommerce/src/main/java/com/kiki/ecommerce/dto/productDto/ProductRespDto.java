package com.kiki.ecommerce.dto.productDto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRespDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer stock;
}

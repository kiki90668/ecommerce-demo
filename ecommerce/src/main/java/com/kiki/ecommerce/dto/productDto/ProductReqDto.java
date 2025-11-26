package com.kiki.ecommerce.dto.productDto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReqDto {
    private Long Id;
    private String name;
    private BigDecimal price;
    private Integer stock;
}

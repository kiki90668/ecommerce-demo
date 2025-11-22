package com.kiki.ecommerce.dto.productDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRespDto {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;
}

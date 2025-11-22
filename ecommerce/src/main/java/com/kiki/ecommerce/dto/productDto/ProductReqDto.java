package com.kiki.ecommerce.dto.productDto;

import lombok.Data;

@Data
public class ProductReqDto {
    private Long Id;
    private String name;
    private Integer price;
    private Integer stock;
}

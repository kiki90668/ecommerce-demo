package com.kiki.ecommerce.service;

import java.util.List;

import com.kiki.ecommerce.dto.productDto.ProductReqDto;
import com.kiki.ecommerce.dto.productDto.ProductRespDto;

public interface ProductService {
    ProductRespDto create(ProductReqDto reqDto);
    ProductRespDto getById(Long id);
    List<ProductRespDto> getAll();
    ProductRespDto update(Long id, ProductReqDto reqDto);
    void delete(Long id);
}

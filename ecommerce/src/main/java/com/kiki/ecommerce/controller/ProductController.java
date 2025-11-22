package com.kiki.ecommerce.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;


import com.kiki.ecommerce.common.ApiResponse;
import com.kiki.ecommerce.dto.productDto.ProductReqDto;
import com.kiki.ecommerce.dto.productDto.ProductRespDto;
import com.kiki.ecommerce.service.impl.ProductServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @PostMapping("/create")
    public ApiResponse<ProductRespDto> create(@RequestBody ProductReqDto reqDto) {
        return ApiResponse.success("Created product successfully", productServiceImpl.create(reqDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductRespDto> getById(@PathVariable Long id) {
        return ApiResponse.success("OK", productServiceImpl.getById(id));
    }

    @GetMapping
    public ApiResponse<List<ProductRespDto>> getAll() {
        return ApiResponse.success("OK", productServiceImpl.getAll());
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductRespDto> update(@PathVariable Long id,  @RequestBody ProductReqDto reqDto) {
        return ApiResponse.success("Updated successfully", productServiceImpl.update(id, reqDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productServiceImpl.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}

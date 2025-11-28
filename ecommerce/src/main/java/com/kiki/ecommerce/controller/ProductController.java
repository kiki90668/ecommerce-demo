package com.kiki.ecommerce.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import com.kiki.ecommerce.common.ApiResponse;
import com.kiki.ecommerce.dto.productDto.ProductReqDto;
import com.kiki.ecommerce.dto.productDto.ProductRespDto;
import com.kiki.ecommerce.service.ProductService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ApiResponse<ProductRespDto> create(@RequestBody ProductReqDto reqDto) {
        return ApiResponse.success("Created product successfully", productService.create(reqDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductRespDto> getById(@PathVariable Long id) {
        return ApiResponse.success("OK", productService.getById(id));
    }

    @GetMapping
    public ApiResponse<List<ProductRespDto>> getAll() {
        return ApiResponse.success("OK", productService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ApiResponse<ProductRespDto> update(@PathVariable Long id,  @RequestBody ProductReqDto reqDto) {
        return ApiResponse.success("Updated successfully", productService.update(id, reqDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}

package com.kiki.ecommerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kiki.ecommerce.dto.productDto.*;
import com.kiki.ecommerce.entity.BizException;
import com.kiki.ecommerce.entity.Product;
import com.kiki.ecommerce.repository.ProductRepository;
import com.kiki.ecommerce.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepo;


    @Override
    //新增商品
    public ProductRespDto create(ProductReqDto reqDto) {
        //檢查產品是否存在
        if (productRepo.existsByName(reqDto.getName())) {
            throw new BizException(400, "Product already exists.");
        }
        Product newProduct = Product.builder()
                .name(reqDto.getName())
                .price(reqDto.getPrice())
                .stock(reqDto.getStock())
                .build();
        
        productRepo.save(newProduct);

        return mapToDto(newProduct);
    }

    @Override
    //取得單一商品
    public ProductRespDto getById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new BizException(404, "Product not found"));

        return mapToDto(product);
    }

    @Override
    //取得所有商品
    public List<ProductRespDto> getAll() {
        List<Product> products = productRepo.findAll();
        return products.stream().map(this::mapToDto).toList();
    }

    @Override
    //更新商品
    public ProductRespDto update(Long id, ProductReqDto reqDto) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new BizException(404, "Product not found"));
        product.setName(reqDto.getName());
        product.setPrice(reqDto.getPrice());
        product.setStock(reqDto.getStock());
        productRepo.save(product);

        return mapToDto(product);
    }

    @Override
    //刪除商品
    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new BizException(404, "Product not found");
        }
        productRepo.deleteById(id);
    }

    //將Product轉換為ProductRespDto
    private ProductRespDto mapToDto(Product product) {
        return ProductRespDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

}

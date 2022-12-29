package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto findById(long id);
    List<ProductDto> listAllProducts();
}

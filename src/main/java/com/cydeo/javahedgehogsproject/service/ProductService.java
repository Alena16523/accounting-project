package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.enums.ProductUnit;

import java.util.List;

public interface ProductService {

    ProductDto findById(long id);
    List<ProductDto> listAllProducts();
    ProductDto update(ProductDto product);
    void save(ProductDto product);
}

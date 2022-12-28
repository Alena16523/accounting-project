package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ProductDto;

public interface ProductService {

    ProductDto findById(long id);
}

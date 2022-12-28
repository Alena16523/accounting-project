package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ProductDTO;

public interface ProductService {

    ProductDTO findById(long id);
}

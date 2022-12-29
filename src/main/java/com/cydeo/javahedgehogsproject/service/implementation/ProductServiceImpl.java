package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.entity.Product;
import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.ProductRepository;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ProductDto findById(long id) {
        Product product = productRepository.findById(id).get();
        return mapperUtil.convert(product,new ProductDto());
    }

    @Override
    public List<ProductDto> listAllProducts() {

        List<Product>productList = productRepository.findAll();
        return productList.stream().map(product -> mapperUtil.convert(product,new ProductDto())).collect(Collectors.toList());
    }

    @Override
    public ProductDto update(ProductDto product) {
        Product product1 = productRepository.save(mapperUtil.convert(product,new Product()));
        return mapperUtil.convert(product1,new ProductDto());
    }

    @Override
    public void save(ProductDto product) {
        productRepository.save(mapperUtil.convert(product,new Product()));
    }


}

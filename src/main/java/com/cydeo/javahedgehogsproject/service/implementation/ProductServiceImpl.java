package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.Product;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.ProductRepository;
import com.cydeo.javahedgehogsproject.service.ProductService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public ProductDto findById(long id) {
        Product product = productRepository.findById(id).get();
        return mapperUtil.convert(product, new ProductDto());
    }

    @Override
    public List<ProductDto> listAllProducts() {
        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Product> productList = productRepository.listProductsByCompany(company);
        return productList.stream().map(product -> mapperUtil.convert(product, new ProductDto())).collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        Product product = productRepository.findById(id).get();
        product.setDeleted(true);
        productRepository.save(product);
    }

    @Override
    public void save(ProductDto productDto) {
        productDto.setQuantityInStock(0);
        Product product = mapperUtil.convert(productDto, new Product());
        productRepository.save(product);
    }

    @Override
    public void update(ProductDto productDto) {
        Product product1 = productRepository.save(mapperUtil.convert(productDto, new Product()));
        mapperUtil.convert(product1, new ProductDto());

    }

}



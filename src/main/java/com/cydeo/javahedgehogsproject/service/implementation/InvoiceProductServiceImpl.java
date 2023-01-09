package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.*;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.service.SecurityService;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;
    private final InvoiceService invoiceService;
    private final ProductService productService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService, @Lazy InvoiceService invoiceService, ProductService productService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceService = invoiceService;
        this.productService = productService;
    }

    @Override
    public BigDecimal totalTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalTax = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalTax = totalTax.add(BigDecimal.valueOf(each.getQuantity()).multiply(each.getTax()).divide(BigDecimal.valueOf(100)).multiply(each.getPrice()));
        }
        return totalTax.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public BigDecimal totalPriceWithoutTax(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);//from entity

        BigDecimal totalPrice = BigDecimal.valueOf(0);
        for (InvoiceProduct each : invoiceProductList) {
            totalPrice = totalPrice.add(each.getPrice().multiply(BigDecimal.valueOf(each.getQuantity())));
        }
        return totalPrice.setScale(2, RoundingMode.CEILING);
    }

    @Override
    public InvoiceService findAllByInvoice(Long id) {
        return null;
    }

    @Override
    public List<InvoiceProductDto> findAllInvoiceProducts(Long invoiceId) {
        List<InvoiceProduct> invoiceProductList = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        List<InvoiceProductDto> DtoList = invoiceProductList.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto())).collect(Collectors.toList());
        for (InvoiceProductDto each : DtoList) {
            BigDecimal x = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice());
            BigDecimal y = BigDecimal.valueOf(each.getQuantity()).multiply(each.getPrice()).multiply(each.getTax()).divide(BigDecimal.valueOf(100));
            each.setTotal(x.add(y).setScale(2, RoundingMode.CEILING));

        }
        return DtoList;
    }

    @Override
    public List<InvoiceProductDto> findAllById(Long id) {
        CompanyDto loggedInCompany = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(loggedInCompany, new Company());

        return invoiceProductRepository.findAllByInvoice_CompanyAndInvoiceId(company, id).stream()
                .filter(invoiceProduct -> !invoiceProduct.isDeleted())
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .peek(invoiceProductDto -> invoiceProductDto.setTotal(calculate(invoiceProductDto)))
                .collect(Collectors.toList());
    }

    private BigDecimal calculate(InvoiceProductDto invoiceProductDto) {
        BigDecimal totalWithOutTax = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity()));
        BigDecimal taxAmount = invoiceProductDto.getPrice().multiply(BigDecimal.valueOf(invoiceProductDto.getQuantity())).multiply(invoiceProductDto.getTax()).divide(new BigDecimal(100));

        return totalWithOutTax.add(taxAmount).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public void saveByInvoiceId(InvoiceProductDto invoiceProduct, Long id) {
        InvoiceDto invoice = invoiceService.findById(id);
        InvoiceProduct product = mapperUtil.convert(invoiceProduct, new InvoiceProduct());
        product.setProfitLoss(new BigDecimal(0));
        product.setInvoice(mapperUtil.convert(invoice, new Invoice()));
        invoice.setInvoiceProducts(List.of(invoiceProduct));
        invoice.setPrice(invoiceProduct.getPrice());
        BigDecimal tax = invoiceProduct.getPrice().multiply(invoiceProduct.getTax());
        invoice.setTax(tax);
        invoice.setTotal(invoiceProduct.getTotal());
        invoiceProductRepository.save(product);
    }

    @Override
    public void delete(Long productId) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(productId).get();
        invoiceProduct.setDeleted(true);
        invoiceProduct.setPrice(new BigDecimal(0));
        invoiceProduct.setTax(new BigDecimal(0));

        invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto) {
        //go to DB find that invoice:
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        //find all invoiceProducts belongs to that invoice:
        List<InvoiceProduct> listInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoice.getId());
        //delete one by one all invoiceProducts that we found base on the id:
        listInvoiceProducts.forEach(invoiceProduct -> {
            Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProduct.getId());
            if (foundInvoiceProduct.isPresent()) {
                foundInvoiceProduct.get().setDeleted(true);
                invoiceProductRepository.save(foundInvoiceProduct.get());
            }
        });
    }

    @Override
    public void saveProduct(InvoiceProductDto invoiceProductDto, Long id) {

        InvoiceDto invoiceDto = invoiceService.findById(id);
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());
        invoiceProduct.setProfitLoss(new BigDecimal(0));
        invoiceProduct.setInvoice(mapperUtil.convert(invoiceDto, new Invoice()));
        invoiceDto.setInvoiceProducts(List.of(invoiceProductDto));
        invoiceDto.setPrice(invoiceProductDto.getPrice());
        BigDecimal tax = invoiceProductDto.getPrice().multiply(invoiceProductDto.getTax());
        invoiceDto.setTax(tax);
        invoiceDto.setTotal(invoiceProductDto.getTotal());
        invoiceProductRepository.save(invoiceProduct);


    }

    @Override
    public void deleteSalesInvoiceProduct(Long invoiceProductId) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(invoiceProductId).get();
        invoiceProduct.setDeleted(true);
        invoiceProduct.setPrice(new BigDecimal(0));
        invoiceProduct.setTax(new BigDecimal(0));
        invoiceProductRepository.save(invoiceProduct);


    }

    @Override
    public void reduceQuantityOfProduct(Long invoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoiceId);
        List<InvoiceProductDto> invoiceProductDtos = invoiceProducts.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());

        int totalQuantity = 0;
        for (InvoiceProductDto each : invoiceProductDtos) {
            totalQuantity += each.getQuantity();
        }

        for (InvoiceProductDto each : invoiceProductDtos) {
            ProductDto productDto = productService.findById(each.getProduct().getId());
            if (productDto.getQuantityInStock() >= totalQuantity) {
                productDto.setQuantityInStock(productDto.getQuantityInStock() - totalQuantity);
                productService.save(productDto);
            } else {
                throw new IllegalArgumentException("Product quantity is not enough!");
            }
        }
    }

    @Override
    public void calculateProfitLossForSale(Long invoiceId) {
        // go to purchase invoice find the approved one
        // check the product is matching with sale invoice
        // then take the quantity amount that I want to sell
        // and based on that quantity calculate the price (quantity*price+(quantity*price*tax/100))
        // take that result subtract from the invoiceProduct total price
        // and save it as profit/loss
    }
}

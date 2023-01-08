package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;


    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, @Lazy InvoiceService invoiceService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
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
}






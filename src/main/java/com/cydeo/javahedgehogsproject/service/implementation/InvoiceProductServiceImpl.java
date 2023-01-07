package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final MapperUtil mapperUtil;
    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductServiceImpl(MapperUtil mapperUtil, InvoiceProductRepository invoiceProductRepository) {
        this.mapperUtil = mapperUtil;
        this.invoiceProductRepository = invoiceProductRepository;
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
    public void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto) {
        //go to DB find that invoice:
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        //find all invoiceProducts belongs to that invoice:
        List<InvoiceProduct> listInvoiceProducts = invoiceProductRepository.findAllByInvoiceId(invoice.getId());
        //delete all invoiceProducts that we found one by one base on id:
        listInvoiceProducts.forEach(invoiceProduct -> {
            Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProduct.getId());
            if(foundInvoiceProduct.isPresent()){
                foundInvoiceProduct.get().setDeleted(true);
                invoiceProductRepository.save(foundInvoiceProduct.get());
            }
        });
    }


}

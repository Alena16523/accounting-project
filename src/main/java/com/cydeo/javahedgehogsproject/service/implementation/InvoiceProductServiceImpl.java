package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
    public void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto) {
        //go to DB find that invoice:
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());
        //find all invoiceProducts belongs to that invoice:
        List<InvoiceProduct> listInvoiceProducts = invoiceProductRepository.findAllByInvoice(invoice);
        //delete all invoiceProducts that we found one by one base on id:
        listInvoiceProducts.forEach(invoiceProduct -> {
            Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProduct.getId());
            if(foundInvoiceProduct.isPresent()){
                foundInvoiceProduct.get().setDeleted(true);
                invoiceProductRepository.save(foundInvoiceProduct.get());
            }
        });

    }



    @Override
    public BigDecimal totalPriceWithoutTax(String invoiceNo) {
        //go to invoiceProduct table, find all base on the Invoice No thru the invoce table
        return invoiceProductRepository.retrieveTotalPriceWithoutTax(invoiceNo);
    }

}

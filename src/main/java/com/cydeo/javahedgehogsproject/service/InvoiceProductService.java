package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceProductService {

    BigDecimal totalTax(Long invoiceId);

    BigDecimal totalPriceWithoutTax(Long invoiceId);

    List<InvoiceProductDto> findAllInvoiceProducts(Long invoiceId);

    void saveProduct(InvoiceProductDto invoiceProductDto,Long id);

    void deleteSalesInvoiceProduct(Long invoiceProductId);


}

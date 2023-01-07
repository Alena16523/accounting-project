package com.cydeo.javahedgehogsproject.service;

import java.math.BigDecimal;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;


public interface InvoiceProductService {

    BigDecimal totalTax(Long invoiceId);

    BigDecimal totalPriceWithoutTax(Long invoiceId);

    void deleteByInvoice(InvoiceType invoiceType, InvoiceDto invoiceDto);
}

package com.cydeo.javahedgehogsproject.service;

import java.math.BigDecimal;

public interface InvoiceProductService {

    BigDecimal totalTax(Long invoiceId);

    BigDecimal totalPriceWithoutTax(Long invoiceId);

    void reduceQuantityOfProduct(Long invoiceId);

}

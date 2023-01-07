package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

public interface InvoiceService {

    InvoiceDto findById(long id);

    InvoiceDto getNewInvoice(InvoiceType invoiceType);

     String InvoiceNo(InvoiceType invoiceType, Long companyId);


}

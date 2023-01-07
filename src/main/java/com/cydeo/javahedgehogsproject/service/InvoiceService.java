package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {

    InvoiceDto findById(long id);
    List<InvoiceDto> findAllInvoice(InvoiceType invoiceType);


}

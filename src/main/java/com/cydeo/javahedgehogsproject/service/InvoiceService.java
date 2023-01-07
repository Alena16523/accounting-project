package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {

    InvoiceDto findById(long id);
    List<InvoiceDto> findAllInvoice(InvoiceType invoiceType);


    InvoiceDto getNewInvoice(InvoiceType invoiceType);

     String InvoiceNo(InvoiceType invoiceType, Long companyId);


    void delete(Long id);

  //  List<InvoiceDto> findAllInvoicesByInvoiceType(InvoiceType invoiceType);

    List<InvoiceDto> findAllInvoice(InvoiceType invoiceType);

}

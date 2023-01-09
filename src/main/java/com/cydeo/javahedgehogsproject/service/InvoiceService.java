package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;

import java.util.List;

public interface InvoiceService {

    InvoiceDto findById(long id);

    List<InvoiceDto> findAllInvoice(InvoiceType invoiceType);

    InvoiceDto getNewSalesInvoice(InvoiceType invoiceType);

    String createInvoiceNoForSalesInvoice(InvoiceType invoiceType, Long companyId);

    InvoiceDto getNewInvoice(InvoiceType invoiceType);

    String InvoiceNo(InvoiceType invoiceType, Long companyId);

    void update(InvoiceDto invoice);

    void delete(Long id);

    InvoiceDto save(InvoiceDto invoiceDto);



}

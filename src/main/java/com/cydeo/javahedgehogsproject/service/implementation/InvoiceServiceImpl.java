package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public InvoiceDto findById(long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return mapperUtil.convert(invoice, new InvoiceDto());
    }


}

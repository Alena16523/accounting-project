package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }


    @Override
    public InvoiceDto findById(long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return mapperUtil.convert(invoice, new InvoiceDto());
    }

    @Override
    public List<InvoiceDto> findAllInvoice(InvoiceType invoiceType) {
        Company company = mapperUtil.convert(securityService.getLoggedInCompany(), new Company());
        List<Invoice> invoiceList = invoiceRepository.findInvoicesByCompanyAndInvoiceType(company,invoiceType);

       return invoiceList.stream().map(invoice -> {
            InvoiceDto invoiceDTO = mapperUtil.convert(invoice, new InvoiceDto());
            invoiceDTO.setTax(100);
            invoiceDTO.setTotal(BigDecimal.ONE);
            invoiceDTO.setPrice(BigDecimal.ONE);
            return invoiceDTO;

        }).collect(Collectors.toList());


    }


}

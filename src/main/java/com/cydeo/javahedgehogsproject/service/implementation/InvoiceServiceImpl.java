package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.entity.*;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.InvoiceProductRepository;
import com.cydeo.javahedgehogsproject.repository.InvoiceRepository;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;

    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;
    private final InvoiceService invoiceService;
    private final InvoiceProductRepository invoiceProductRepository;


    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceProductService invoiceProductService, @Lazy InvoiceService invoiceService, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
        this.invoiceService = invoiceService;
        this.invoiceProductRepository = invoiceProductRepository;
    }

    @Override
    public InvoiceDto findById(long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return mapperUtil.convert(invoice, new InvoiceDto());
    }

    @Override
    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        invoice.setDeleted(true);
        invoiceRepository.save(invoice);
        //delete all invoiceProducts belongs to the deleted invoice:
        invoiceProductService.deleteByInvoice(InvoiceType.SALES,mapperUtil.convert(invoice, new InvoiceDto()));
    }
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
    @Override
    public List<InvoiceDto> findAllInvoicesByInvoiceType(InvoiceType invoiceType) {
        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(companyDto, new Company());
        //go to DB, get all invoices belongs to login in the system:
       List<Invoice> invoices = invoiceRepository.findAllByCompanyIdAndInvoiceType(company.getId(), invoiceType);
        return invoices.stream().map(invoice -> {

            InvoiceDto obj = mapperUtil.convert(invoice, new InvoiceDto());
//                    obj.setPrice(invoiceProductService.totalPriceWithoutTax(invoice.getInvoiceNo()));
//                    obj.setTax(invoiceProductService.totalTax(invoice.getInvoiceNo()));
//                    obj.setTotal(invoiceProductService.totalPriceWithTax(invoice.getInvoiceNo()));
//                    obj.setInvoiceProducts((invoiceService.findById(invoice.getId())).getInvoiceProducts());

                    obj.setPrice(BigDecimal.ONE);
                    obj.setTax(20);
                    obj.setTotal(BigDecimal.ONE);
                    return obj;
                }).collect(Collectors.toList());
    }

}

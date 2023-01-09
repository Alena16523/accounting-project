package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceProductDto;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.service.InvoiceProductService;
import com.cydeo.javahedgehogsproject.service.ReportingService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportingServiceImpl implements ReportingService {

    private final InvoiceProductService invoiceProductService;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public ReportingServiceImpl(InvoiceProductService invoiceProductService, SecurityService securityService, MapperUtil mapperUtil) {
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Map<String, BigDecimal> profitLoss() {
        Map<String, BigDecimal> profitLoss = new HashMap<>();


        return profitLoss;
    }

    @Override
    public List<InvoiceProductDto> getInvoiceProductsOfApprovedInvoices() {
        CompanyDto currentCompany = securityService.getLoggedInCompany();
        List<InvoiceProduct> allInvoiceProducts = invoiceProductService.getAllApprovedInvoiceProductsByCompany(currentCompany);

        return allInvoiceProducts.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .collect(Collectors.toList());
    }

}

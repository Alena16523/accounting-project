package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.InvoiceDto;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.dto.CurrencyDto;
import com.cydeo.javahedgehogsproject.dto.UsdDto;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import com.cydeo.javahedgehogsproject.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final InvoiceService invoiceService;
    private final CurrencyClient currencyClient;

    public DashboardServiceImpl(InvoiceService invoiceService, CurrencyClient currencyClient) {

        this.invoiceService = invoiceService;
        this.currencyClient = currencyClient;
    }

    @Override
    public UsdDto getCurrency() {
        CurrencyDto currencies = currencyClient.getCurrencies();
        return currencies.getUsd();
    }

    @Override
    public Map<String, BigDecimal> dashboardNumbers(){

        Map<String, BigDecimal> totalNumbersToDisplay=new HashMap<>();
        totalNumbersToDisplay.put("totalCost", calculateTotalCost());
        totalNumbersToDisplay.put("totalSales", calculateTotalSales());
        totalNumbersToDisplay.put("Profit / Loss", calculateProfitLoss());

        return totalNumbersToDisplay;
    }


    @Override
    public BigDecimal calculateTotalCost() {
        BigDecimal total=invoiceService.findAllInvoice(InvoiceType.PURCHASE).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .map(InvoiceDto::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    @Override
    public BigDecimal calculateTotalSales() {
        BigDecimal total=invoiceService.findAllInvoice(InvoiceType.SALES).stream()
                .filter(invoiceDto -> invoiceDto.getInvoiceStatus().equals(InvoiceStatus.APPROVED))
                .map(InvoiceDto::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total;
    }

    @Override
    public BigDecimal calculateProfitLoss() {

        BigDecimal profitLoss=calculateTotalSales().subtract(calculateTotalCost());

        return profitLoss;
    }

}

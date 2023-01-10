package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.dto.Currency;
import com.cydeo.javahedgehogsproject.dto.Usd;
import com.cydeo.javahedgehogsproject.service.DashboardService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final CurrencyClient currencyClient;

    public DashboardServiceImpl(CurrencyClient currencyClient) {
        this.currencyClient = currencyClient;
    }

    @Override
    public Usd getCurrency() {
        Currency currencies = currencyClient.getCurrencies();
        return currencies.getUsd();
    }
}

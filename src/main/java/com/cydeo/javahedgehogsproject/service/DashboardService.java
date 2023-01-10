package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.client.CurrencyClient;
import com.cydeo.javahedgehogsproject.dto.Currency;

import java.util.List;

public interface DashboardService {
    List<Currency> getCurrencies();
}

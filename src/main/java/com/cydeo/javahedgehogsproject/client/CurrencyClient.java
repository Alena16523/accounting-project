package com.cydeo.javahedgehogsproject.client;

import com.cydeo.javahedgehogsproject.dto.Currency;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://cdn.jsdelivr.net", name = "CURRENCY-CLIENT")
public interface CurrencyClient {

    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    List<Currency> getCurrencies();

}

package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.InvoiceDto;

public interface InvoiceService {

    InvoiceDto findById(long id);

}

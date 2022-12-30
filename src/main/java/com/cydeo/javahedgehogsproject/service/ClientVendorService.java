package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {

    ClientVendorDto findById (Long id);
    List<ClientVendorDto> findAll();

}

package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

    CompanyDto findById(Long id);
    List<CompanyDto> findAll();

}

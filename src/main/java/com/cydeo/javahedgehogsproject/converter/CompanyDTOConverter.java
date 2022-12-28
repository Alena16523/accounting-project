package com.cydeo.javahedgehogsproject.converter;

import com.cydeo.javahedgehogsproject.dto.CompanyDTO;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CompanyDTOConverter implements Converter <String, CompanyDTO> {
    CompanyService companyService;

    public CompanyDTOConverter(@Lazy CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public CompanyDTO convert(String source) {
        if (source == null || source.equals("")) {
            return null;
        }

        return companyService.findById(source);
    }
}

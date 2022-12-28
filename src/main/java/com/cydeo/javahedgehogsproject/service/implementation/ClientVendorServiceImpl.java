package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.ClientVendorRepository;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import org.springframework.stereotype.Service;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {
private final ClientVendorRepository clientVendorRepository;
private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public ClientVendorDto findById(Long id) {
        return mapperUtil.convert(clientVendorRepository.findById(id),new ClientVendorDto());
    }
}

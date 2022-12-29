package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.ClientVendorDto;
import com.cydeo.javahedgehogsproject.entity.ClientVendor;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.ClientVendorRepository;
import com.cydeo.javahedgehogsproject.service.ClientVendorService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return mapperUtil.convert(clientVendorRepository.findById(id), new ClientVendorDto());
    }

    @Override
    public List<ClientVendorDto> findAll() {
        List<ClientVendor> clientVendorList = clientVendorRepository.findAll(Sort.by("clientVendorType", "clientVendorName").ascending());
        return clientVendorList.stream().map(clientVendor -> mapperUtil.convert(clientVendor, new ClientVendorDto())).collect(Collectors.toList());
    }

}

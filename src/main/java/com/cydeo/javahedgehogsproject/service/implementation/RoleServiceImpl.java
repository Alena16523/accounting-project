package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.RoleDto;
import com.cydeo.javahedgehogsproject.entity.Role;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.RoleRepository;
import com.cydeo.javahedgehogsproject.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public RoleDto findById(long id) {
        Role role = roleRepository.findById(id).get();
        return mapperUtil.convert(role,new RoleDto());
    }

    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(role -> mapperUtil.convert(role, new RoleDto()))
                .collect(Collectors.toList());
    }

}

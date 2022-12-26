package com.cydeo.javahedgehogsproject.service;

import java.util.List;

public interface RoleService {

    RoleDTO findById(Long id);
    List<RoleDTO> findAllRoles();
}

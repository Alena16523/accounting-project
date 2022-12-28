package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.UserDto;

public interface UserService {

    UserDto findByUsername(String username);

    UserDto findById(long id);
}

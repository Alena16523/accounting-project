package com.cydeo.javahedgehogsproject.service;

import com.cydeo.javahedgehogsproject.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);

    UserDto findById(long id);

    List<UserDto> findAll();




}

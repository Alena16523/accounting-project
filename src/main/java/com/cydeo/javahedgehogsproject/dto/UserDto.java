package com.cydeo.javahedgehogsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
    private String phone;
    private RoleDto role;
    private CompanyDto company;
    private Boolean isOnlyAdmin;

}

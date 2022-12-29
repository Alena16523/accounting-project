package com.cydeo.javahedgehogsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

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
    @NotNull
    private RoleDto role;
    @NotNull
    private CompanyDto company;
    private Boolean isOnlyAdmin;

}

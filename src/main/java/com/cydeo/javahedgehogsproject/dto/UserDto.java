package com.cydeo.javahedgehogsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public UserDto(Long id, String firstname, String lastname, String username, String password, String confirmPassword, String phone, RoleDto role, CompanyDto company) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.phone = phone;
        this.role = role;
        this.company = company;
        if (role.getDescription().equals("Admin")) {
            isOnlyAdmin = true;
        }
    }
}

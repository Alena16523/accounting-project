package com.cydeo.javahedgehogsproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    @NotBlank
    @Size(max = 15, min = 2)
    private String firstname;
    @NotBlank
    @Size(max = 15, min = 2)
    private String lastname;
    @NotBlank
    @Email
    private String username;
    @NotBlank
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,}")
    private String password;
    @NotNull
    private String confirmPassword;
    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
    private String phone;
    @NotNull
    private RoleDto role;
    @NotNull
    private CompanyDto company;
    private Boolean isOnlyAdmin;

}

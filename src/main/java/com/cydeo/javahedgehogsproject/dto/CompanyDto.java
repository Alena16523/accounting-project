package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.enums.CompanyStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String title;
    @NotBlank
    @Pattern(regexp = "^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$") // 8 to 11 digits
    private String phone;
    @NotBlank
    @Pattern(regexp = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
    private String website;
    @NotNull
    private AddressDto address;
    private CompanyStatus companyStatus;

}

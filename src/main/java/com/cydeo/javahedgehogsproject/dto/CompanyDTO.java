package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.enums.CompanyStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {

    private Long id;
    private String title;
    private String phone;
    private String website;
    private AddressDTO address;
    private CompanyStatus companyStatus;

}

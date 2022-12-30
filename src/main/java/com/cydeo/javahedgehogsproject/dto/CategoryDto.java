package com.cydeo.javahedgehogsproject.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.*;


@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    @NotBlank
    @Size(max = 100, min = 2)
    private String description;
    private CompanyDto company;
    private boolean hasProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public boolean isHasProduct() {
        return hasProduct;
    }

    public void setHasProduct(boolean hasProduct) {
        this.hasProduct = hasProduct;
    }
}

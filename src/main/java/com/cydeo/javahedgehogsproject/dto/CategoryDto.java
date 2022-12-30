package com.cydeo.javahedgehogsproject.dto;

import lombok.*;

import javax.validation.constraints.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Size(max = 100, min = 2)
    private String description;
    private CompanyDto company;
    private boolean hasProduct;



    public String getDescription() {
        return description;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public boolean isHasProduct() {
        return hasProduct;
    }



    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompany(CompanyDto company) {
        this.company = company;
    }

    public void setHasProduct(boolean hasProduct) {
        this.hasProduct = hasProduct;
    }
}

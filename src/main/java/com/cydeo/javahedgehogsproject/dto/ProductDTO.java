package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private Integer quantityInStock;
    private Integer lowLimitAlert;
    private ProductUnit productUnit;
    private CategoryDto category;

}

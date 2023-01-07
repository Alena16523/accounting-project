package com.cydeo.javahedgehogsproject.dto;

import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import com.cydeo.javahedgehogsproject.enums.InvoiceStatus;
import com.cydeo.javahedgehogsproject.enums.InvoiceType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {
    private Long id;
    private String invoiceNo;
    private InvoiceStatus invoiceStatus;
    private InvoiceType invoiceType;
    @DateTimeFormat()
    private LocalDate date;
    private CompanyDto company;
    private ClientVendorDto clientVendor;
    private BigDecimal price;
    private Integer tax;
    private BigDecimal total;
    private List<InvoiceProductDto> invoiceProducts;


}

package com.cydeo.javahedgehogsproject.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="invoice_products")
public class InvoiceProduct extends  BaseEntity{

    private int quantity;
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal profitLoss;
    private int remainingQty;
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private Product product;

}

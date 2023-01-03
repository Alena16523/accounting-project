package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

}

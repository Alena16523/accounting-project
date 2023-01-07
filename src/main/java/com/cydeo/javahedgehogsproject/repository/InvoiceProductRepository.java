package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.Invoice;
import com.cydeo.javahedgehogsproject.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {

    List<InvoiceProduct> findAllByInvoice(Invoice invoice);
    @Query("SELECT COUNT(ip.price) FROM InvoiceProduct ip WHERE ip.invoice.invoiceNo = ?1 AND ip.invoice.invoiceType <> 'SALE'")
    BigDecimal retrieveTotalPriceWithoutTax(String invoiceNo);



}

package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.ClientVendor;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query("SELECT p FROM Product p JOIN Category c ON p.category.id=c.id WHERE c.company=?1")
    List<Product> listProductsByCompany(Company company);
}

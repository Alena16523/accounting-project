package com.cydeo.javahedgehogsproject.repository;
import com.cydeo.javahedgehogsproject.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long>{
}
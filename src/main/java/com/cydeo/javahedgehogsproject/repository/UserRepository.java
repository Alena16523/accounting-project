package com.cydeo.javahedgehogsproject.repository;

import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.Role;
import com.cydeo.javahedgehogsproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByCompanyIs(Company company);

//    List<User> findAllByRoleIsNot();

}

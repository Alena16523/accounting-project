package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.entity.Category;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.entity.User;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.CategoryRepository;
import com.cydeo.javahedgehogsproject.repository.UserRepository;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final CompanyService companyService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserRepository userRepository, SecurityService securityService, CompanyService companyService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.securityService = securityService;
        this.companyService = companyService;
    }


    @Override
    public CategoryDto findById(long id) {
        Category category = categoryRepository.findById(id).get();
        return mapperUtil.convert(category,new CategoryDto());
    }

    @Override
    public List<CategoryDto> listAllCategoriesByUser() {
        //getting all categories from DB
        UserDto loggedInUser=securityService.getLoggedInUser();
        User loggedInEntityUser=mapperUtil.convert(loggedInUser, new User());

       CompanyDto companyDto= companyService.findById(loggedInEntityUser.getCompany().getId());
       Company company=mapperUtil.convert(companyDto, new Company());

        List<Category> listOfCategories=categoryRepository.findAllByCompanyId(company.getId());

        //converting one by one category to DTO and returning List
        return listOfCategories.stream().map(category->mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());
    }
}

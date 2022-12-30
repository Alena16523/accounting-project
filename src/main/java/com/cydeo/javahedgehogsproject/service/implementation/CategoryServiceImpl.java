package com.cydeo.javahedgehogsproject.service.implementation;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.entity.Category;
import com.cydeo.javahedgehogsproject.entity.Company;
import com.cydeo.javahedgehogsproject.mapper.MapperUtil;
import com.cydeo.javahedgehogsproject.repository.CategoryRepository;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final SecurityService securityService;
    private final CompanyService companyService;


    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserService userService, SecurityService securityService, CompanyService companyService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.securityService = securityService;
        this.companyService = companyService;
    }


    @Override
    public CategoryDto findById(long id) {
        Category category = categoryRepository.findById(id).get();
        return mapperUtil.convert(category, new CategoryDto());
    }

    @Override
    public List<CategoryDto> listAllCategoriesByUser() {
        //getting all categories from DB

//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserDto userDto = userService.findByUsername(username);
//        CompanyDto companyDto = companyService.findById(userDto.getCompany().getId());
//        Company company = mapperUtil.convert(companyDto, new Company());

        CompanyDto companyDto = securityService.getLoggedInCompany();
        Company company = mapperUtil.convert(companyDto, new Company());

        List<Category> listOfCategories = categoryRepository.findAllByCompanyId(company.getId());

        //converting one by one category to DTO and returning List
        return listOfCategories.stream().map(category -> mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());
    }
    @Override
    public List<CategoryDto> listAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category->mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
    }
}


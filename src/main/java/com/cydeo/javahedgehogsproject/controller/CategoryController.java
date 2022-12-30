package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.CategoryDto;
import com.cydeo.javahedgehogsproject.dto.CompanyDto;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/categories")
public class CategoryController {

       private final CategoryService categoryService;

       public CategoryController(CategoryService categoryService) {
              this.categoryService = categoryService;
       }

       @GetMapping("/list")
       public String retrieveAllCategories(Model model) {

              model.addAttribute("categories", categoryService.listAllCategoriesByUser());

              return "category/category-list";
       }


       @GetMapping("/create")
       public String createCategory(Model model) {

              model.addAttribute("newCategory", new CategoryDto());

              return "/category/category-create";

       }

       @PostMapping("/create")
       public String insertCategory(@ModelAttribute("newCategory") CategoryDto category) {

              categoryService.save(category);

              return "redirect:/categories/list";

       }



       }


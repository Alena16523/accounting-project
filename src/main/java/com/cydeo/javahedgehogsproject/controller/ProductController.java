package com.cydeo.javahedgehogsproject.controller;


import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }


    @GetMapping("/list")
    public String listAllProducts(Model model){
        model.addAttribute("products",productService.listAllProducts());
        return "/product/product-list";
    }


    @GetMapping("update/{id}")
    public String editProduct(@PathVariable ("id")Long id,Model model){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("productUnits", ProductUnit.values());
        return "/product/product-update";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id")Long id){
        productService.delete(id);
        return "redirect:/products/list";



    }






    }




package com.cydeo.javahedgehogsproject.controller;


import com.cydeo.javahedgehogsproject.dto.ProductDto;
import com.cydeo.javahedgehogsproject.enums.ProductUnit;
import com.cydeo.javahedgehogsproject.service.CategoryService;
import com.cydeo.javahedgehogsproject.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public ProductController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.findAll() );
        model.addAttribute("productUnits", ProductUnit.values());
        model.addAttribute("products", productService.listAllProducts());


        return "/product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct( @ModelAttribute("newProduct") ProductDto newProduct, Model model) {

            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("productUnits", ProductUnit.values());

        productService.save(newProduct);
        return "redirect:/products/list";
    }


    @PostMapping("/update/{id}")
    public String updateProduct( @ModelAttribute("product") ProductDto product, Model model) {

          model.addAttribute("categories",categoryService.findAll());
          model.addAttribute("productUnits", ProductUnit.values());
        productService.update(product);
        return "redirect:/products/list";
    }

    }


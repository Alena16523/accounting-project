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
    @GetMapping("/list")
    public String listProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.listAllCategories() );
        model.addAttribute("productUnits", ProductUnit.values());
        model.addAttribute("products", productService.listAllProducts());

        return "/product/product-list";
    }

    @GetMapping("/create")
    public String createProduct(Model model) {
        model.addAttribute("newProduct", new ProductDto());
        model.addAttribute("categories", categoryService.listAllCategories() );
        model.addAttribute("productUnits", ProductUnit.values());
        model.addAttribute("products", productService.listAllProducts());


        return "/product/product-create";
    }

    @PostMapping("/create")
    public String insertProduct( @ModelAttribute("newProduct") ProductDto newProduct, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("categories", categoryService.listAllCategories());
            model.addAttribute("productUnits", ProductUnit.values());
            return "/product/product-create";

        }
        productService.save(newProduct);
        return "redirect:/products/list";
    }

    @GetMapping("/update/{id}")
    public String editProduct(@PathVariable("id")Long id, Model model){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("categories",categoryService.listAllCategories());
        model.addAttribute("productUnits", ProductUnit.values());
        return "/product/product-update";
    }

    @PostMapping("/update/{id}")
    public String updateProduct( @ModelAttribute("product") ProductDto product, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("products", productService.listAllProducts());
            model.addAttribute("categories",categoryService.listAllCategories());
            model.addAttribute("productUnits", ProductUnit.values());

            return "/task/update";

        }

        productService.update(product);

        return "redirect:/products/list";

    }
    }


package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final RoleService roleService;
    private final CompanyService companyService;
    private final UserService userService;

    public UserController(RoleService roleService, CompanyService companyService, UserService userService) {
        this.roleService = roleService;
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "/user/user-list";
    }

}

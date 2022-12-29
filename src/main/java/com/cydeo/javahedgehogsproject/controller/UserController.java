package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.SecurityService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final RoleService roleService;
    private final CompanyService companyService;
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(RoleService roleService, CompanyService companyService, UserService userService,@Lazy SecurityService securityService) {
        this.roleService = roleService;
        this.companyService = companyService;
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "user/user-list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("companies", companyService.findAll());

        return "user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("newUser") UserDto user) {

        userService.save(user);

        return "redirect:/users/list";

    }
}

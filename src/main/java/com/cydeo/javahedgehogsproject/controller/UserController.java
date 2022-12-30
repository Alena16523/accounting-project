package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.service.CompanyService;
import com.cydeo.javahedgehogsproject.service.RoleService;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.cydeo.javahedgehogsproject.dto.UserDto;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

        model.addAttribute("users", userService.findAll());

        return "/user/user-list";
    }

    @GetMapping("/create")
    public String createUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("companies", companyService.findAllByUsers());

        return "user/user-create";
    }

    @PostMapping("/create")
    public String insertUser(@ModelAttribute("newUser") UserDto user) {

        userService.save(user);

        return "redirect:/users/list";

    }

    @GetMapping("/update/{id}")
    public String editUser(@PathVariable("id") long id, Model model) {

        model.addAttribute("user", userService.findById(id));
        model.addAttribute("userRoles", roleService.findAll());
        model.addAttribute("companies", companyService.findAllByUsers());

        return "/user/user-update";

    }

    @PostMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") UserDto user) {

        userService.update(user);

        return "redirect:/users/list";

    }


}

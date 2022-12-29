package com.cydeo.javahedgehogsproject.controller;

import com.cydeo.javahedgehogsproject.dto.UserDto;
import com.cydeo.javahedgehogsproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

            model.addAttribute("user", userService.findAll());

        return "user/user-list";
    }


}

package com.gtja.myspringmvc.controller;

import com.gtja.myspringmvc.annotation.Controller;
import com.gtja.myspringmvc.annotation.Qualifier;
import com.gtja.myspringmvc.annotation.RequestMapping;
import com.gtja.myspringmvc.service.UserService;

@Controller("userController")
@RequestMapping("/user")
public class UserController {
    @Qualifier("userServiceImpl")
    private UserService userService;

    @RequestMapping("/insert")
    public void insert(){
        userService.insert();
    }
}

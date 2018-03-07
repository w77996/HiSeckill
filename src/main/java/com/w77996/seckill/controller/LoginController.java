package com.w77996.seckill.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class LoginController {

    @RequestMapping("login")
    public String toLogin(){
        return "login";
    }


}

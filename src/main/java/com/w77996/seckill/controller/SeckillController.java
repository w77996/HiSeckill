package com.w77996.seckill.controller;

import com.w77996.seckill.redis.RedisService;
import com.w77996.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class SeckillController {

    @Autowired
    UserService userService;
    @Autowired
    RedisService redisService;
    @RequestMapping("/")
    public String hello(Model model){
        model.addAttribute("name", "Joshua");
        return "hello";
    }
}

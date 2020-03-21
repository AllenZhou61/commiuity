package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @auther Allen
 * @date 2020/3/21 - 20:53
 */
@Controller
public class IndexController {
    @GetMapping("/")
    public String Index(){
        return "index";
    }
}

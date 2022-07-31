package com.training.taggadagram.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @PostMapping(value = "/home")
    public String home(){
        return "hello world";
    }
}

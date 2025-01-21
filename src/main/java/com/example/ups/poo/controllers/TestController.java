package com.example.ups.poo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "hello world, this is my first SpringBoot project";
    }
    @GetMapping("/greet/{name}/{lastname}")
    public String greet(@PathVariable String name, @PathVariable String lastname){
        return "Hello " + name + " " + lastname + ", this is my first SpringBoot project...!";
    }
    @GetMapping("/hello")
    public String hello(@RequestParam String name, @RequestParam String lastname){
        return "Hello " + name + " " + lastname + ", this is my first SpringBoot project xxx!";
    }
}

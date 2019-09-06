package com.javabrains.springboot.tutorial.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String homePage() {
        return "<big> Home Page </big>";
    }

    @RequestMapping("/hello")
    public String sayHi() {
        return "Hi";
    }
}

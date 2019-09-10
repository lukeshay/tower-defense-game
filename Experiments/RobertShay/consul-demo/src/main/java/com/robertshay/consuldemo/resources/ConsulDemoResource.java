package com.robertshay.consuldemo.resources;

import com.robertshay.consuldemo.models.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsulDemoResource {
    @RequestMapping("/hello")
    public Model hello() {
        return new Model("Hello");
    }

}

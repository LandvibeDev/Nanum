package com.landvibe.nanum.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
    @GetMapping("/")
    String home() {
        return "Hello World !";
    }

}

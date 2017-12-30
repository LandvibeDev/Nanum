package com.landvibe.nanum.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public String main() {
        return "index";
    }

    @GetMapping("/studies/**")
    @ResponseStatus(value = HttpStatus.OK)
    public String study() {
        return "index";
    }


}

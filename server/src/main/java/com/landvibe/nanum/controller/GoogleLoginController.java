package com.landvibe.nanum.controller;

import com.landvibe.nanum.model.GoogleToken;
import com.landvibe.nanum.model.GoogleUser;
import com.landvibe.nanum.repository.GoogleUserRepository;
import com.landvibe.nanum.service.GoogleLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/login")
public class GoogleLoginController {

    private final GoogleLoginService service;

    @Autowired
    public GoogleLoginController(GoogleLoginService service) {
        this.service = service;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public String main() {
        return "index";
    }

    @GetMapping(path = "/google")
    public String googleLogin() {
        String uriForCode = service.getUriForCode();
        return "redirect:" + uriForCode;
    }

    @GetMapping(path = "/google/auth")
    public String googleLoginAuth(@RequestParam(value = "code") String code) {
        service.login(code);
        return "redirect:/";
    }

//    @GetMapping(value = {"/signup", "/signup/{username}"})
//    public String signup() {
//        return "index";
//    }
}
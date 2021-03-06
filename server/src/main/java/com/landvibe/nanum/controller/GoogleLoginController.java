package com.landvibe.nanum.controller;

import com.landvibe.nanum.model.GoogleToken;
import com.landvibe.nanum.model.GoogleUser;
//import com.landvibe.nanum.repository.GoogleUserRepository;
import com.landvibe.nanum.model.User;
import com.landvibe.nanum.service.GoogleLoginService;
import com.mysql.fabric.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.CookieGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class GoogleLoginController {

    private final GoogleLoginService googleLoginService;

    @Autowired
    public GoogleLoginController(GoogleLoginService googleLoginService) {
        this.googleLoginService = googleLoginService;
    }

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public String main() {
        return "index";
    }

    @GetMapping("/google")
    public String googleLogin() throws UnsupportedEncodingException {
        String uriForCode = googleLoginService.getUriForCode();
        return "redirect:" + uriForCode;
    }

    @GetMapping("/google/oauth2callback")
    public String googleLoginOAuth2CallBack(@RequestParam(value = "code") String code,
                                  HttpSession session, HttpServletResponse response) throws UnsupportedEncodingException {

        CookieGenerator cookieGenerator = new CookieGenerator();
        googleLoginService.login(code, session, cookieGenerator, response);
        return "redirect:/";
    }

    // this function will be removed
    // simple login for application test
    @PostMapping("/simple")
    public String simpleLogin(@RequestBody Map<String,String> body, HttpSession session, HttpServletResponse response){
        try{
            googleLoginService.simpleLogin(body.get("username"),session,response);
        }catch(Exception e){

        }


        return "redirect:/";
    }




//    @GetMapping(value = {"/signup", "/signup/{username}"})
//    public String signup() {
//        return "index";
//    }
}
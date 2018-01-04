package com.landvibe.nanum.controller;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public String main() {
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        session.invalidate();
//        session.removeAttribute("id");
//        session.setAttribute("id", null);
        Cookie cookie = new Cookie("isLogin","false");
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/studies/**")
    @ResponseStatus(value = HttpStatus.OK)
    public String study(HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        } else {
            System.out.println("session exist! " + session.getAttribute("user"));
            return "index";
        }
    }
}
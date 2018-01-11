package com.landvibe.nanum.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @GetMapping("")
    @ResponseStatus(value = HttpStatus.OK)
    public String issueList() {
        return "index";
    }

    @GetMapping("/new")
    public String issueCreateTemplate() {
        return "index";
    }

    @GetMapping("/{issueId}")
    @ResponseStatus(value = HttpStatus.OK)
    public String issueItem(@PathVariable long issueId) {
        return "index";
    }
}

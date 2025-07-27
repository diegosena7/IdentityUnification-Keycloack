package com.auth.contractservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

    @GetMapping("/api")
    public String api() {
        return swagger();
    }

    @GetMapping("/openapi")
    public String openapi() {
        return swagger();
    }

    @GetMapping("/swagger")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }
    

}

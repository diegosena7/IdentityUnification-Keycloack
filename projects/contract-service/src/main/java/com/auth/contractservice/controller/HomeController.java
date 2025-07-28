package com.auth.contractservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.auth.contractservice.service.CustomerService;


@Controller
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String getIndex(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());

            var customerEntity = customerService.searchCustomerById(principal.getClaimAsString("sub"));

            model.addAttribute("customer", customerEntity);

            //System.out.println(customerEntity.toString());

        }

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

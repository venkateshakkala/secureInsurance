package com.secureInsurance.customerServices.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secureInsurance")
public class HomeController {

    @GetMapping("/home")
    public String homePage() {
        return "forward:/home.html";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "forward:/register.html";
    }

    @GetMapping("/products")
    public String productsPage() {
        return "forward:/products.html";
    }

    @GetMapping("/buyPolicy")
    public String buyPolicyPage(){
        return "forward:/proposal.html";
    }
}

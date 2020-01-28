package com.egconley.codefellowship;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CFController {

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }
}

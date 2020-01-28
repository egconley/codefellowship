package com.egconley.codefellowship;

import org.springframework.stereotype.Controller;

@Controller
public class CFController {
    public String getHome() {
        return "home";
    }
}

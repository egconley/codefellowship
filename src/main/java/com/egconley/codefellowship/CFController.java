package com.egconley.codefellowship;

import com.egconley.codefellowship.models.AppUser;
import com.egconley.codefellowship.models.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CFController {

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/")
    public String getHome() {
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String password, String email, String firstName, String lastName, String dateOfBirth, String bio) {
        AppUser newUser = new AppUser(username, password, email, firstName, lastName, dateOfBirth, bio);
        appUserRepository.save(newUser);
        return new RedirectView("/");
    }
}

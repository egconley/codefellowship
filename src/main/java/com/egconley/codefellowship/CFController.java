package com.egconley.codefellowship;

import com.egconley.codefellowship.models.AppUser;
import com.egconley.codefellowship.models.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

@Controller
public class CFController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @GetMapping("/")
    public String getHome(Principal p, Model m) {
        if (p != null) {
            System.out.println(p.getName());
            AppUser user = appUserRepository.findByUsername(p.getName());
            m.addAttribute("username", p.getName());
            m.addAttribute("user", user);
        } else {
            System.out.println("not logged in");
        }
        return "home";
    }

    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(String username, String password, String email, String firstName, String lastName, String dateOfBirth, String bio) {
        if (appUserRepository.findByUsername(username)==null) {
            AppUser newUser = new AppUser(username, encoder.encode(password), email, firstName, lastName, dateOfBirth, bio);
            appUserRepository.save(newUser);
            return new RedirectView("/");
        } else {
            return new RedirectView("/signup?taken=true");
        }
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

}

package com.egconley.codefellowship;

import com.egconley.codefellowship.models.AppUser;
import com.egconley.codefellowship.models.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

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
            AppUser loggedInUser = appUserRepository.findByUsername(p.getName());
            m.addAttribute("username", p.getName());
            m.addAttribute("loggedInUser", loggedInUser);
            m.addAttribute("users", appUserRepository.findAll());
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

            Authentication authentication = new UsernamePasswordAuthenticationToken(newUser, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new RedirectView("/");
        } else {
            return new RedirectView("/signup?taken=true");
        }
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/users/{id}")
    public String getUserAccount(Model m, @PathVariable Long id, Principal p) {
        AppUser loggedInUser = appUserRepository.findByUsername(p.getName());
        if (id==loggedInUser.getId()) {
            m.addAttribute("loggedInUser", appUserRepository.getOne(id));
            return "users";
        } else {
            return "login";
        }
    }

    @GetMapping("/users/{id}/profile")
    public String getProfile(Model m, @PathVariable Long id, Principal p) {

        AppUser loggedInUser = appUserRepository.findByUsername(p.getName());

        m.addAttribute("loggedInUser", loggedInUser);
        m.addAttribute("user", appUserRepository.getOne(id));

        return "profile";
    }

    @PostMapping("/users/{id}/follow")
    public RedirectView follow(@PathVariable Long id, Principal p) {

        AppUser loggedInUser = appUserRepository.findByUsername(p.getName());
        AppUser user = appUserRepository.getOne(id);

        loggedInUser.getUsersIFollow().add(user);
        appUserRepository.save(loggedInUser);

        return new RedirectView("profile");
    }

}

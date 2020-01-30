package com.egconley.codefellowship;

import com.egconley.codefellowship.models.AppUser;
import com.egconley.codefellowship.models.AppUserRepository;
import com.egconley.codefellowship.models.Post;
import com.egconley.codefellowship.models.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PostRepository postRepository;

    @PostMapping("/posts")
    public RedirectView createPost(String body, Principal p) {

        AppUser user = appUserRepository.findByUsername(p.getName());

        Post post = new Post(body, new Date(), user);

        postRepository.save(post);
        return new RedirectView("/");
    }
}

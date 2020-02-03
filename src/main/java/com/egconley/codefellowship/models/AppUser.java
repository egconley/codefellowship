package com.egconley.codefellowship.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String dateOfBirth;
    String bio;

    @OneToMany(mappedBy = "user")
    List<Post> posts;

    // Something to keep track of all the users I follow
    @ManyToMany
    @JoinTable(
            name = "usersFollowUsers",
            joinColumns = { @JoinColumn(name = "follower_id")},
            inverseJoinColumns = { @JoinColumn(name = "followed_id")}
    )
    List<AppUser> usersIFollow;

    // Something to keep track of all the users that follow me
    @ManyToMany(mappedBy = "usersIFollow")
    List<AppUser> usersThatFollowMe;

    public AppUser() {}

    public AppUser(String username, String password, String email, String firstName, String lastName, String dateOfBirth, String bio) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bio = bio;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBio() {
        return bio;
    }

    public List<Post> getPosts() { return posts; }

    public List<AppUser> getUsersIFollow() { return usersIFollow; }

    public List<AppUser> getUsersThatFollowMe() { return usersThatFollowMe; }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

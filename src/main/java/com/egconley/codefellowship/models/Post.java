package com.egconley.codefellowship.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String body;
    Date createdAt;

    @ManyToOne
    AppUser user;

    public Post () {}

    public Post(String body, Date createdAt, AppUser user) {
        this.body = body;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public AppUser getUser() {
        return user;
    }
}

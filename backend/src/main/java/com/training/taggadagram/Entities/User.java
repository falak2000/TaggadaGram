package com.training.taggadagram.Entities;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;


@Document("User")
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    private String profile_pic;

    private String salt;
    private List<String> listFollowers;
    private List<String> listFollowing;
}

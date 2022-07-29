package com.training.taggadagram.Entities;

import com.training.taggadagram.config.ValidPassword;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Document("User")
public class UserSign {
    @Id
    private String id;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    @Email
    private String email;

    @NotNull
    @ValidPassword
    private String password;

    private String role;
    private String profile_pic;

    private List<String> listFollowers;
    private List<String> listFollowing;

    private String salt;
    private String token;

    public List<String> getListFollowers() {
        return listFollowers;
    }

    public void setListFollowers(List<String> listFollowers) {
        this.listFollowers = listFollowers;
    }

    public List<String> getListFollowing() {
        return listFollowing;
    }

    public void setListFollowing(List<String> listFollowing) {
        this.listFollowing = listFollowing;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public UserSign() {
    };

    public UserSign(UserSign userSign){
        this.id=userSign.id;
        this.firstname=userSign.firstname;
        this.lastname=userSign.lastname;
        this.email=userSign.email;
        this.password=userSign.password;
        this.profile_pic=userSign.profile_pic;
        this.role=userSign.role;
        this.salt=userSign.salt;
        this.listFollowers=userSign.listFollowers;
        this.listFollowing=userSign.listFollowing;
    }
}

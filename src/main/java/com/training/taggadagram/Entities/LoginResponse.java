package com.training.taggadagram.Entities;

public class LoginResponse {
    private boolean status ;
    private String message;

    private UserSign userSign;

    public UserSign getUserSign() {
        return userSign;
    }

    public void setUserSign(UserSign userSign) {
        this.userSign = userSign;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

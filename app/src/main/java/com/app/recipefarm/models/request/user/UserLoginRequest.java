package com.app.recipefarm.models.request.user;

public class UserLoginRequest {
    public String email;
    public String password;

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

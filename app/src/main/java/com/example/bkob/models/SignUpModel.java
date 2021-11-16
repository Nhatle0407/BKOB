package com.example.bkob.models;

import android.text.TextUtils;
import android.util.Patterns;

public class SignUpModel {
    private String name;
    private String email;
    private String password;
    private String rePassword;

    public SignUpModel(String name, String email, String password, String rePassword) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.rePassword = rePassword;
    }

    public boolean isValidName(){
        return !TextUtils.isEmpty(name);
    }

    public boolean isValidEmail(){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isValidPassword(){
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }

    public boolean isValidRePassword(){
        return password.equals(rePassword);
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRePassword() {
        return rePassword;
    }
}

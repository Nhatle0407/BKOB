package com.example.bkob.views.interfaces;

public interface LoginInterface {
    void emailInvalid();
    void passwordInvalid();
    void loginSuccess();
    void loginError(Exception e);
}

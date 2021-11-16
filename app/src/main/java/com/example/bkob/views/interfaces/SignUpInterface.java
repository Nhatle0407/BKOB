package com.example.bkob.views.interfaces;

public interface SignUpInterface {
    void nameInvalid();
    void emailInvalid();
    void passwordInvalid();
    void rePasswordInvalid();
    void signUpSuccess();
    void signUpError(Exception e);
}

package com.example.bkob.presenters;

import androidx.annotation.NonNull;

import com.example.bkob.models.LoginModel;
import com.example.bkob.views.interfaces.LoginInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private LoginInterface loginInterface;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public boolean validation(LoginModel loginModel){
        if(!loginModel.isValidEmail()){
            loginInterface.emailInvalid();
            return false;
        }
        else if(!loginModel.isValidPassword()){
            loginInterface.passwordInvalid();
            return false;
        }
        else {
            return true;
        }
    }

    public void login(LoginModel loginModel){
        if(!validation(loginModel)){
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(loginModel.getEmail(), loginModel.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                loginInterface.loginSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                loginInterface.loginError(e);
            }
        });
    }
}

package com.example.bkob.presenters;

import androidx.annotation.NonNull;

import com.example.bkob.models.SignUpModel;
import com.example.bkob.views.interfaces.SignUpInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter {
    private SignUpInterface signUpInterface;

    public SignUpPresenter(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }

    public boolean validation(SignUpModel signUpModel){
        if(!signUpModel.isValidEmail()){
            signUpInterface.emailInvalid();
            return false;
        }
        else if(!signUpModel.isValidPassword()){
            signUpInterface.passwordInvalid();
            return false;
        }
        else if(!signUpModel.isValidRePassword()){
            signUpInterface.rePasswordInvalid();
            return false;
        }
        else {
            return true;
        }
    }

    public void signUp(SignUpModel signUpModel){
        if(!validation(signUpModel)){
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(signUpModel.getEmail(), signUpModel.getPassword()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                signUpInterface.signUpSuccess();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signUpInterface.signUpError(e);
            }
        });
    }
}

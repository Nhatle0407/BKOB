package com.example.bkob.presenters;

import androidx.annotation.NonNull;

import com.example.bkob.models.SignUpModel;
import com.example.bkob.views.interfaces.SignUpInterface;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpPresenter {
    private SignUpInterface signUpInterface;

    public SignUpPresenter(SignUpInterface signUpInterface) {
        this.signUpInterface = signUpInterface;
    }

    public boolean validation(SignUpModel signUpModel){
        if(!signUpModel.isValidName()){
            signUpInterface.nameInvalid();
            return false;
        }
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
                FirebaseUser user = auth.getCurrentUser();
                saveUserData(user, signUpModel);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                signUpInterface.signUpError(e);
            }
        });
    }

    private void saveUserData(FirebaseUser user, SignUpModel signUpModel) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", ""+user.getUid());
        hashMap.put("name", ""+signUpModel.getName());
        hashMap.put("email", ""+signUpModel.getEmail());
        hashMap.put("phone", "");
        hashMap.put("avatar", "");
        hashMap.put("address", "");
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        userRef.child(user.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
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

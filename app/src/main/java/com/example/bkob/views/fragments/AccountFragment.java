package com.example.bkob.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentAccountBinding;
import com.example.bkob.databinding.FragmentCartBinding;
import com.example.bkob.views.activity.AuthenticationActivity;
import com.example.bkob.views.activity.MainActivity;
import com.example.bkob.views.activity.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    TextView userName, userEmail, userPhoneNumber;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    DatabaseReference databaseReference;
    DatabaseReference userRef;

    boolean isLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
            userRef = databaseReference.child(firebaseUser.getUid());
            isLogin = true;
        }
        else {
            isLogin = false;
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);

        if (isLogin){
            displayUserInfo();
        }
        setUpButton();

    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void setUpButton(){
        binding.btnYourProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    replaceFragment(new SellingFragment());
                    getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext(), "Hãy đăng nhập trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnOrderReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLogin){
                    replaceFragment(new ReceiveFragment());
                    getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext(), "Hãy đăng nhập trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnShoppingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    replaceFragment(new BuyHistoryFragment());
                    getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext(), "Hãy đăng nhập trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SupportFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            }
        });
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    replaceFragment(new ChangePasswordFragment());
                    getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext(), "Hãy đăng nhập trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin){
                    replaceFragment(new ChangeInfoFragment());
                    getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(getContext(), "Hãy đăng nhập trước", Toast.LENGTH_SHORT).show();
                }
            }
        });
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
    }

    private void logOut(){
        Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
        startActivity(intent);
        if (isLogin){
            firebaseAuth.signOut();
        }
        getActivity().finish();
    }

    private void displayUserInfo(){
        userName = binding.userName;
        userEmail = binding.userEmail;
        userPhoneNumber = binding.userPhoneNumber;

        userEmail.setText(firebaseUser.getEmail());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    String value = ds.getValue().toString();

                    switch (uid){
                        case "name":
                            userName.setText(value);
                            break;
                        case "phone":
                            userPhoneNumber.setText(value);
                            break;
                        default:
                            break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("account", databaseError.getMessage());
            }
        });
    }
}

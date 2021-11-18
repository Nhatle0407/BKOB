package com.example.bkob.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentChangePasswordBinding;
import com.example.bkob.databinding.FragmentReceiveBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordFragment extends Fragment {

    FragmentChangePasswordBinding binding;
    String currentPassword, newPassword, confirmPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangePasswordBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBackChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccountFragment());
            }
        });

        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .commit();
    }

    private void changePassword(){
        currentPassword = binding.inputCurrentPassword.getText().toString();
        newPassword = binding.inputNewPassword.getText().toString();
        confirmPassword = binding.inputConfirmPassword.getText().toString();
        if (!newPassword.equals(confirmPassword)){
            Toast.makeText(getContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();;
        String email = user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(email, currentPassword);

        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(getContext(), "Thay đổi thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                replaceFragment(new AccountFragment());
                            }
                        }
                    });
                }else {
                    Toast.makeText(getContext(), "Thay đổi thất bại. Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bkob.databinding.FragmentResetPasswordBinding;
import com.example.bkob.views.customView.CustomProgressDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordFragment extends Fragment {
    FragmentResetPasswordBinding binding;
    private CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(getLayoutInflater());
        dialog = new CustomProgressDialog(getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickReset();
            }
        });
    }

    private void clickReset() {
        dialog.show();
        String email = binding.etEmail.getText().toString();
        if(TextUtils.isEmpty(email)){
            binding.etEmail.setError("Vui lòng nhập email!");
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    dialog.hide();
                    Toast.makeText(getContext(), "Đường link reset mật khẩu đã được gửi tới email của bạn!", Toast.LENGTH_SHORT).show();
                    binding.etEmail.setText("");
                }
                else{
                    dialog.hide();
                    Toast.makeText(getContext(), "Đã có lỗi xảy ra. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
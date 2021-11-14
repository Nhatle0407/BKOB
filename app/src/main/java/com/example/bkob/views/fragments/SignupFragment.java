package com.example.bkob.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentSignupBinding;
import com.example.bkob.models.SignUpModel;
import com.example.bkob.presenters.SignUpPresenter;
import com.example.bkob.views.activity.MainActivity;
import com.example.bkob.views.customView.CustomProgressDialog;
import com.example.bkob.views.interfaces.SignUpInterface;

public class SignupFragment extends Fragment implements SignUpInterface {
    private FragmentSignupBinding binding;
    private SignUpPresenter presenter;
    private CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new CustomProgressDialog(getContext());

        presenter = new SignUpPresenter(this);

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.tvAskLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LoginFragment());
            }
        });

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignUp();
            }
        });
    }

    private void clickSignUp() {
        dialog.show();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String rePassword = binding.etRePassword.getText().toString().trim();

        SignUpModel signUpModel = new SignUpModel(email, password, rePassword);
        presenter.signUp(signUpModel);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.authFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }


    @Override
    public void emailInvalid() {
        dialog.hide();
        binding.etEmail.setError("Email không hợp lệ!");
    }

    @Override
    public void passwordInvalid() {
        dialog.hide();
        binding.etPassword.setError("Password phải nhiều hơn 6 kí tự!");
    }

    @Override
    public void rePasswordInvalid() {
        dialog.hide();
        binding.etRePassword.setError("Mật khẩu không khớp!");
    }

    @Override
    public void signUpSuccess() {
        dialog.hide();
        Toast.makeText(getContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void signUpError(Exception e) {
        dialog.hide();
        Toast.makeText(getContext(), "Đăng ký thất bại: "+e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
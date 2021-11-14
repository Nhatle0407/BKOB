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
import com.example.bkob.databinding.FragmentLoginBinding;
import com.example.bkob.models.LoginModel;
import com.example.bkob.presenters.LoginPresenter;
import com.example.bkob.views.activity.MainActivity;
import com.example.bkob.views.customView.CustomProgressDialog;
import com.example.bkob.views.interfaces.LoginInterface;


public class LoginFragment extends Fragment implements LoginInterface {
    private FragmentLoginBinding binding;

    private LoginPresenter loginPresenter;

    private CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new CustomProgressDialog(getContext());

        loginPresenter = new LoginPresenter(this);

        binding.tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainActivity();
            }
        });

        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ResetPasswordFragment());
            }
        });

        binding.tvAskSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new SignupFragment());
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogin();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void clickLogin() {
        dialog.show();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        LoginModel loginModel = new LoginModel(email, password);
        loginPresenter.login(loginModel);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.authFragments, fragment)
                .addToBackStack(null)
                .commit();
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
    public void loginSuccess() {
        dialog.hide();
        Toast.makeText(getContext(), "Đăng nhập thành công!" , Toast.LENGTH_SHORT).show();
        goToMainActivity();
    }

    @Override
    public void loginError(Exception e) {
        dialog.hide();
        Toast.makeText(getContext(), "Đăng nhập thất bại: " + e.getMessage() , Toast.LENGTH_SHORT).show();
    }
}
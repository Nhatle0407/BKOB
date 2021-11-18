package com.example.bkob.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentOrderBinding;
import com.example.bkob.models.UserModel;
import com.example.bkob.presenters.OrderPresenter;
import com.example.bkob.views.customView.CustomProgressDialog;
import com.example.bkob.views.customView.SuccessDialog;
import com.example.bkob.views.interfaces.OrderInterface;

public class OrderFragment extends Fragment implements OrderInterface {
    private FragmentOrderBinding binding;
    private OrderPresenter orderPresenter;
    private CustomProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderBinding.inflate(getLayoutInflater());
        orderPresenter = new OrderPresenter(getContext(), this);
        dialog = new CustomProgressDialog(getContext());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderPresenter.loadUserInfo();

        binding.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        binding.btnConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

        binding.btnBackOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void confirmOrder() {
        dialog.show();
        String address = binding.edtUserAddress.getText().toString();
        String phone = binding.edtUserSdt.getText().toString();
        if(TextUtils.isEmpty(address)){
            binding.edtUserAddress.setError("Vui lòng nhập địa chỉ!");
            return;
        }
        if(TextUtils.isEmpty(phone)){
            binding.edtUserSdt.setError("Vui lòng nhập số điện thoại!");
            return;
        }
        orderPresenter.confirmOrder();
    }

    @Override
    public void showUserInfo(UserModel userModel) {
        binding.tvUserName.setText(userModel.getName());
        binding.edtUserAddress.setText(userModel.getAddress());
        binding.edtUserSdt.setText(userModel.getAddress());
    }

    @Override
    public void orderSuccess() {
        dialog.hide();
        SuccessDialog successDialog = new SuccessDialog(getContext());
        successDialog.show();
        replaceFragment(new HomeFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
}
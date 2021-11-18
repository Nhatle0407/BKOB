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
import com.example.bkob.databinding.FragmentAccountBinding;
import com.example.bkob.databinding.FragmentCartBinding;
import com.example.bkob.views.activity.AuthenticationActivity;
import com.example.bkob.views.activity.MainActivity;
import com.example.bkob.views.activity.SplashActivity;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);

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
                replaceFragment(new SellingFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            }
        });
        binding.btnOrderReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ReceiveFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            }
        });
        binding.btnShoppingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new BuyHistoryFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
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
                replaceFragment(new ChangePasswordFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
            }
        });
        binding.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new ChangeInfoFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
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
        getActivity().finish();
    }
}

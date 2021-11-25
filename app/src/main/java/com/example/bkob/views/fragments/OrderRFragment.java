package com.example.bkob.views.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bkob.R;
import com.example.bkob.Singleton.DetailSaleSingleton;
import com.example.bkob.Singleton.OrderSingleton;
import com.example.bkob.databinding.FragmentAccountBinding;
import com.example.bkob.databinding.FragmentOrderRBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.models.UserModel;
import com.example.bkob.views.adapters.ReceiveAdapter;
import com.example.bkob.views.interfaces.OrderRInterface;

import java.util.ArrayList;
import java.util.List;

public class OrderRFragment extends Fragment  {
    FragmentOrderRBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderRBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);

        binding.nameInforOrder.setText(OrderSingleton.getNotifyModel().getBookName());
        binding.userInforReceive.setText(OrderSingleton.getNotifyModel().getFromName());
        binding.userAddressReceive.setText(OrderSingleton.getNotifyModel().getAddress());
        binding.userSdt.setText(OrderSingleton.getNotifyModel().getPhone());
        binding.userDateOrder.setText(OrderSingleton.getNotifyModel().getDate());
        binding.sumMoneyOrderReceive.setText(String.format("%,dđ", Integer.parseInt(OrderSingleton.getNotifyModel().getTotal().toString())));

        binding.btnBackOrderReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }


}
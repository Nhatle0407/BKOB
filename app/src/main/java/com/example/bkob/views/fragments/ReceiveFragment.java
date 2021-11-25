package com.example.bkob.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bkob.R;
import com.example.bkob.Singleton.DetailSaleSingleton;
import com.example.bkob.Singleton.OrderSingleton;
import com.example.bkob.databinding.FragmentReceiveBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.presenters.RecievePresenter;
import com.example.bkob.views.adapters.ReceiveAdapter;
import com.example.bkob.views.interfaces.DetailSaleInterface;
import com.example.bkob.views.interfaces.OrderRInterface;
import com.example.bkob.views.interfaces.RecieveInterface;

import java.util.ArrayList;
import java.util.List;


public class ReceiveFragment extends Fragment implements RecieveInterface {
    FragmentReceiveBinding binding;
    private RecievePresenter recievePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReceiveBinding.inflate(getLayoutInflater());
        recievePresenter = new RecievePresenter(getContext(), this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recievePresenter.loadNotify();
        binding.btnBackReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccountFragment());
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

    @Override
    public void showRecieve(ReceiveAdapter adapter) {
        binding.rclReceive.setAdapter(adapter);
        adapter.onClick(new OrderRInterface() {
            @Override
            public void showDetail(NotifyModel notifyModel) {
                replaceFragment(new OrderRFragment());
                OrderSingleton.setNotifyModel(notifyModel);
            }
        });
    }
}
package com.example.bkob.views.fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentAccountBinding;
import com.example.bkob.databinding.FragmentOrderRBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.models.UserModel;
import com.example.bkob.views.adapters.ReceiveAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderRFragment extends Fragment {
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

    }

}
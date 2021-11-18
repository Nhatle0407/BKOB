package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.Singleton.CartSingleton;
import com.example.bkob.Singleton.DetailSingleton;
import com.example.bkob.databinding.FragmentDetailBinding;
import com.example.bkob.databinding.FragmentHomeBinding;
import com.squareup.picasso.Picasso;


public class DetailFragment extends Fragment {
    FragmentDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });
        binding.nameItemDetail.setText(DetailSingleton.getBookModel().getName());
        binding.price.setText(DetailSingleton.getBookModel().getPrice());
        binding.desDetail.setText(DetailSingleton.getBookModel().getDescription());
        try{
            Picasso.get().load(DetailSingleton.getBookModel().getImageUrl()).into(binding.avatarDetail);
        }
        catch (Exception e){
            Log.d("CART", "Fail to load image:"+e.getMessage());
        }
        binding.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new OrderFragment());
            }
        });
        binding.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartSingleton.getBookModels().add(DetailSingleton.getBookModel());
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
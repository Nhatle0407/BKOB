package com.example.bkob.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentBuyHistoryBinding;
import com.example.bkob.databinding.FragmentSellingBinding;
import com.example.bkob.presenters.SellingPresenter;
import com.example.bkob.views.adapters.SellingAdapter;
import com.example.bkob.views.interfaces.SellingInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SellingFragment extends Fragment implements SellingInterface {

    FragmentSellingBinding binding;
    SellingPresenter sellingPresenter;
    RecyclerView sellingRv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSellingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);

        sellingPresenter = new SellingPresenter(getContext(), this);
        sellingRv = binding.rclSelling;

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            sellingPresenter.loadSelling();
        }
        else{
            binding.notLogin.setVisibility(View.VISIBLE);
            binding.btnFloatAddBook.setVisibility(View.GONE);
        }

        binding.btnFloatAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new AddBookFragment());
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .commit();
    }

    @Override
    public void sellingEmpty() {

    }

    @Override
    public void showSelling(SellingAdapter adapter) {
        sellingRv.setAdapter(adapter);
    }
}
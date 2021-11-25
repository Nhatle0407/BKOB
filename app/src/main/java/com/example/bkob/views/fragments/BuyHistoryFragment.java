package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentBuyHistoryBinding;
import com.example.bkob.databinding.FragmentChangeInfoBinding;
import com.example.bkob.presenters.BuyPresenter;
import com.example.bkob.views.adapters.BuyAdapter;
import com.example.bkob.views.interfaces.BuyInterface;

public class BuyHistoryFragment extends Fragment implements BuyInterface {

    FragmentBuyHistoryBinding binding;
    private BuyPresenter buyPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBuyHistoryBinding.inflate(getLayoutInflater());
        buyPresenter = new BuyPresenter(getContext(), this);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buyPresenter.loadBuy();

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
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
                .commit();
    }

    @Override
    public void showBuy(BuyAdapter adapter) {
        binding.rvBuyHistory.setAdapter(adapter);
    }
}
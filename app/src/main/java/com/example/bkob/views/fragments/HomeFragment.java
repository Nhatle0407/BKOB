package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentHomeBinding;
import com.example.bkob.presenters.HomePresenter;
import com.example.bkob.views.adapters.CategoryAdapter;
import com.example.bkob.views.interfaces.HomeInterface;
import com.facebook.shimmer.ShimmerFrameLayout;

public class HomeFragment extends Fragment implements HomeInterface {
    private FragmentHomeBinding binding;
    private HomePresenter homePresenter;
    private RecyclerView categoryRv;
    private ShimmerFrameLayout shimmerCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
        homePresenter = new HomePresenter(this, getContext());
        categoryRv = binding.rvCategory;
        shimmerCategory = binding.shimmerCategory;

        loadCategory();
        binding.btnCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new CartFragment());
                getActivity().findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
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

    private void loadCategory() {
        homePresenter.loadCategory();
    }

    @Override
    public void showCategory(CategoryAdapter adapter) {
        shimmerCategory.stopShimmer();
        shimmerCategory.setVisibility(View.GONE);
        categoryRv.setAdapter(adapter);
    }
}
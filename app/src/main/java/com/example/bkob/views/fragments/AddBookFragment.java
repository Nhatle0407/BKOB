package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentAddBookBinding;
import com.example.bkob.models.CategoryModel;
import com.example.bkob.views.adapters.CategoryDropdownAdapter;

import java.util.ArrayList;
import java.util.List;


public class AddBookFragment extends Fragment {

    private FragmentAddBookBinding binding;

    private String[] items = {"Loại 1", "Loại 2" , "Loại 3"};
    private CategoryDropdownAdapter categoryDropdownAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBookBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryDropdownAdapter = new CategoryDropdownAdapter(getContext(), R.layout.spinner_selected, getListCategory());
        binding.spinner.setAdapter(categoryDropdownAdapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private List<CategoryModel> getListCategory() {
        List<CategoryModel> list = new ArrayList<>();
        list.add(new CategoryModel("Loại 1"));
        list.add(new CategoryModel("Loại 2"));
        list.add(new CategoryModel("Loại 3"));

        return list;
    }
}
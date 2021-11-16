package com.example.bkob.views.fragments;

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
import com.example.bkob.databinding.FragmentReceiveBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.views.adapters.ReceiveAdapter;
import com.example.bkob.views.interfaces.DetailSaleInterface;

import java.util.ArrayList;
import java.util.List;


public class ReceiveFragment extends Fragment {
    List<ReceiveModel> receiveModelList;
    FragmentReceiveBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReceiveBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add();
        ReceiveAdapter receiveAdapter = new ReceiveAdapter(receiveModelList, new DetailSaleInterface() {
            @Override
            public void detailSale(int i) {
                DetailSaleSingleton.setReceiveModel(receiveModelList.get(i));
                replaceFragment(new OrderRFragment());
            }
        });
        binding.rclReceive.setAdapter(receiveAdapter);
        binding.btnBackReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccountFragment());
            }
        });
    }
    private void add(){
        receiveModelList = new ArrayList<>();
        receiveModelList.add(new ReceiveModel(new BookModel("Giải tích 1", "Với nhiều nội dung quan trọng như tích phân, lượng giác, đạo hàm, nội dung chi tiết rõ ràng giúp bạn qua môn dễ dàng hơn", "Giáo trình", "20000", "1", "P0hDk7huZuVd4d1ihtPagHdWYYI2", "https://firebasestorage.googleapis.com/v0/b/bkob-a0229.appspot.com/o/book_image%2FP0hDk7huZuVd4d1ihtPagHdWYYI21636977469078?alt=media&token=abe51fd6-4f45-408d-8916-536c0e098512"), "22/10/2021", "idfghj"));
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }
}
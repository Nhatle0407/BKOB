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
import com.example.bkob.databinding.FragmentCartBinding;
import com.example.bkob.databinding.FragmentHomeBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.views.adapters.CartAdapter;
import com.example.bkob.views.interfaces.CartInterface;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {
    FragmentCartBinding binding;
    List<BookModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        add();
        CartAdapter cartAdapter = new CartAdapter( list);
        binding.rclCart.setAdapter(cartAdapter);
        binding.btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
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
    private void add(){
        list = new ArrayList<>();
        list.add(new BookModel("Giải tích 1", "Với nhiều nội dung quan trọng như tích phân, lượng giác, đạo hàm, nội dung chi tiết rõ ràng giúp bạn qua môn dễ dàng hơn", "Giáo trình", "20000", "1", "P0hDk7huZuVd4d1ihtPagHdWYYI2", "https://firebasestorage.googleapis.com/v0/b/bkob-a0229.appspot.com/o/book_image%2FP0hDk7huZuVd4d1ihtPagHdWYYI21636977469078?alt=media&token=abe51fd6-4f45-408d-8916-536c0e098512"));
        list.add(new BookModel("Giải tích 1", "Với nhiều nội dung quan trọng như tích phân, lượng giác, đạo hàm, nội dung chi tiết rõ ràng giúp bạn qua môn dễ dàng hơn", "Giáo trình", "20000", "1", "P0hDk7huZuVd4d1ihtPagHdWYYI2", "https://firebasestorage.googleapis.com/v0/b/bkob-a0229.appspot.com/o/book_image%2FP0hDk7huZuVd4d1ihtPagHdWYYI21636977469078?alt=media&token=abe51fd6-4f45-408d-8916-536c0e098512"));
    }
}
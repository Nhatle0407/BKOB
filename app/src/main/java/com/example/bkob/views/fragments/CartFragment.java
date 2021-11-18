package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.Singleton.CartSingleton;
import com.example.bkob.databinding.FragmentCartBinding;
import com.example.bkob.models.BookModel;
import com.example.bkob.presenters.CartPresenter;
import com.example.bkob.views.adapters.CartAdapter;
import com.example.bkob.views.interfaces.CartInterface;
import com.example.bkob.views.interfaces.ChangeInterface;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment implements CartInterface {
    FragmentCartBinding binding;
    CartPresenter cartPresenter;
    RecyclerView cartRv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartPresenter = new CartPresenter(getContext(), this);
        cartRv = binding.rclCart;

                cartPresenter.loadCart();

        binding.btnBackCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        binding.btnBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBuyNow();
            }
        });

    }

    private void clickBuyNow() {

        replaceFragment(new OrderFragment());
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void cartEmpty() {
        binding.btnBuyNow.setVisibility(View.INVISIBLE);
        binding.tvCartEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCart(CartAdapter adapter) {
        cartRv.setAdapter(adapter);
        binding.tvQuantity.setText(adapter.getItemCount() + " sản phẩm");

        adapter.remove(new ChangeInterface() {
            @Override
            public void changeCart() {
                binding.tvQuantity.setText(adapter.getItemCount() + " sản phẩm");
            }
        });
        Log.d("CART", "" + adapter.getItemCount());
        Log.d("CART", adapter.getTotal());
    }

}
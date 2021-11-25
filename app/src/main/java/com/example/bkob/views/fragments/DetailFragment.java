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

import com.example.bkob.R;
import com.example.bkob.Singleton.CartSingleton;
import com.example.bkob.Singleton.DetailSingleton;
import com.example.bkob.databinding.FragmentDetailBinding;
import com.example.bkob.databinding.FragmentHomeBinding;
import com.example.bkob.models.BookModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            binding.btnAddToCart.setVisibility(View.GONE);
            binding.purchase.setVisibility(View.GONE);
        }
        binding.btnBackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new HomeFragment());
            }
        });
        binding.btnCartDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new CartFragment());
            }
        });
        binding.nameItemDetail.setText(DetailSingleton.getBookModel().getName());
        binding.price.setText(String.format("%,dđ", Integer.parseInt(DetailSingleton.getBookModel().getPrice())));
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
                addToCard(DetailSingleton.getBookModel());
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
    private void addToCard(BookModel bookModel) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("carts");
        HashMap<String, String> hashMap = new HashMap<>();
        String timeStamp = ""+System.currentTimeMillis();
        String uid = FirebaseAuth.getInstance().getUid();
        hashMap.put("bookId", bookModel.getBookId());

        cartRef.child(uid).orderByChild("bookId").equalTo(bookModel.getBookId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(getContext(), "Sách đã có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    cartRef.child(uid).child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
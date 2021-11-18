package com.example.bkob.presenters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bkob.models.BookModel;
import com.example.bkob.models.CategoryModel;
import com.example.bkob.views.adapters.BookAdapter;
import com.example.bkob.views.adapters.CartAdapter;
import com.example.bkob.views.interfaces.CartInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartPresenter {
    private Context context;
    private CartInterface cartInterface;
    private ArrayList<BookModel> bookList;
    private ArrayList<String> bookIdList;
    private CartAdapter cartAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public CartPresenter(Context context, CartInterface cartInterface) {
        this.context = context;
        this.cartInterface = cartInterface;
    }

    public void loadCart(){
        bookIdList = new ArrayList<>();
        bookList = new ArrayList<>();
        loadId();
    }

    public void loadId(){
        DatabaseReference cartRef = database.getReference("carts");
        int quantity = bookList.size();
        long total = 0;
        cartRef.child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    bookIdList.clear();
                    DataSnapshot snapshot = task.getResult();
                    if (snapshot.hasChildren()) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String bookId = ds.child("bookId").getValue().toString();
                            bookIdList.add(bookId);
                        }
                        DatabaseReference bookref = database.getReference("books");
                        for(String id : bookIdList){
                            bookref.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if(task.isSuccessful()){
                                        BookModel bookModel = task.getResult().getValue(BookModel.class);
                                        bookList.add(bookModel);
                                    }
                                    cartAdapter = new CartAdapter(bookList);
                                    cartInterface.showCart(cartAdapter);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("CART", "Fail to load book");
                                }
                            });
                        }
                    }
                    else{
                        cartInterface.cartEmpty();
                    }
                }
            }
        });
    }


    private void initAdapter() {
        cartAdapter = new CartAdapter(bookList);
        cartInterface.showCart(cartAdapter);
    }

    private void loadCartItem() {
        DatabaseReference bookref = database.getReference("books");
        for(String id : bookIdList){
            bookref.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        BookModel bookModel = task.getResult().getValue(BookModel.class);
                        Log.d("CART", bookModel.getName() + "/" + bookModel.getImageUrl());
                        bookList.add(bookModel);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("CART", "Fail to load book");
                }
            });
        }
    }
}

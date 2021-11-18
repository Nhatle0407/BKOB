package com.example.bkob.presenters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bkob.models.BookModel;

import com.example.bkob.views.adapters.SellingAdapter;
import com.example.bkob.views.interfaces.SellingInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SellingPresenter {
    private Context context;
    private SellingInterface sellingInterface;
    private ArrayList<BookModel> bookList;
    private ArrayList<String> bookIdList;
    private SellingAdapter sellingAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public SellingPresenter(Context context, SellingInterface sellingInterface){
        this.context = context;
        this.sellingInterface = sellingInterface;
    }

    public void loadSelling(){
        bookIdList = new ArrayList<>();
        bookList = new ArrayList<>();
        loadId();
    }

    public void loadId(){
        DatabaseReference sellingRef = database.getReference("selling");
        sellingRef.child(FirebaseAuth.getInstance().getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                                    sellingAdapter = new SellingAdapter(bookList);
                                    sellingInterface.showSelling(sellingAdapter);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("SELLING", "Fail to load book");
                                }
                            });
                        }
                    }
                    else{
                        sellingInterface.sellingEmpty();
                    }
                }
            }
        });
    }

    private void initAdapter(){
        sellingAdapter = new SellingAdapter(bookList);
        sellingInterface.showSelling(sellingAdapter);

    }

    private void loadSellingItem() {
        DatabaseReference bookref = database.getReference("books");
        for(String id : bookIdList){
            bookref.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        BookModel bookModel = task.getResult().getValue(BookModel.class);
                        Log.d("SELLING", bookModel.getName() + "/" + bookModel.getImageUrl());
                        bookList.add(bookModel);
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("SELLING", "Fail to load book");
                }
            });
        }
    }


}

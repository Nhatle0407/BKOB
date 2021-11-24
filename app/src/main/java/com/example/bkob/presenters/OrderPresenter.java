package com.example.bkob.presenters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bkob.models.BookModel;
import com.example.bkob.models.UserModel;
import com.example.bkob.views.adapters.CartAdapter;
import com.example.bkob.views.interfaces.OrderInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class OrderPresenter {
    private Context context;
    private OrderInterface orderInterface;
    private ArrayList<BookModel> bookList;
    private ArrayList<String> bookIdList;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private UserModel userModel;

    public OrderPresenter(Context context, OrderInterface orderInterface) {
        this.context = context;
        this.orderInterface = orderInterface;
    }

    public void loadUserInfo(){
        String uid = FirebaseAuth.getInstance().getUid();

        DatabaseReference userRef = database.getReference("users");
        userRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    userModel = task.getResult().getValue(UserModel.class);
                    orderInterface.showUserInfo(userModel);
                }
            }
        });
    }

    public void confirmOrder(){
        DatabaseReference cartRef = database.getReference("carts");
        bookIdList = new ArrayList<>();
        bookList = new ArrayList<>();
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
                                        pushOrder(bookModel);
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("ORDER", "Fail to load book");
                                }
                            });
                        }
                    }
                }
            }
        });
    }

    private void pushNotification(String uid, String bookName, String fromName, String address, String phone, String total, String date, String orderId) {
        DatabaseReference notifyRef = database.getReference("notifycations");
        String notifyId = "notify" + System.currentTimeMillis();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("notifyId", ""+notifyId);
        hashMap.put("bookName", ""+bookName);
        hashMap.put("fromName", ""+fromName);
        hashMap.put("date", ""+date);
        hashMap.put("address", ""+address);
        hashMap.put("phone", ""+phone);
        hashMap.put("total", ""+total);
        hashMap.put("orderId", ""+orderId);
        hashMap.put("status", "0");

        notifyRef.child(uid).child(notifyId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                orderInterface.orderSuccess();
                deleteCart();
            }
        });
    }

    private void deleteCart() {
        DatabaseReference cartRef = database.getReference("carts");
        cartRef.child(userModel.getUserId()).removeValue();
    }

    private void pushOrder(BookModel bookModel) {
        DatabaseReference orderRef = database.getReference("orders");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String date = formatter.format(new Date());

            Log.d("ORDER", bookModel.getName());
            String orderId = "order" + System.currentTimeMillis();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("orderId", ""+orderId);
            hashMap.put("bookId", ""+bookModel.getBookId());
            hashMap.put("bookName", ""+bookModel.getName());
            hashMap.put("fromUid", ""+userModel.getUserId());
            hashMap.put("fromName", ""+userModel.getName());
            hashMap.put("toUid", ""+bookModel.getUserId());
            hashMap.put("address", ""+userModel.getAddress());
            hashMap.put("phone", ""+userModel.getPhone());
            hashMap.put("date", ""+date);
            hashMap.put("status", "unconfirmed");

            orderRef.child(orderId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    pushNotification(bookModel.getUserId(), bookModel.getName(), userModel.getName(),userModel.getAddress(), userModel.getPhone(), bookModel.getPrice(), date, orderId);
                }
            });
        }
}

package com.example.bkob.presenters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.bkob.models.BuyModel;
import com.example.bkob.views.adapters.BuyAdapter;
import com.example.bkob.views.interfaces.BuyInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BuyPresenter {
    private Context context;
    private BuyInterface buyInterface;
    private ArrayList<BuyModel> buyModels;
    private BuyAdapter buyAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public BuyPresenter(Context context, BuyInterface buyInterface) {
        this.context = context;
        this.buyInterface = buyInterface;
    }

    public void loadBuy(){
        buyModels = new ArrayList<>();
        DatabaseReference buyRef = database.getReference("buy");
        buyRef.child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                buyModels.clear();
                if(snapshot.hasChildren()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        BuyModel buyModel = ds.getValue(BuyModel.class);
                        buyModels.add(buyModel);
                    }
                    buyAdapter = new BuyAdapter(buyModels);
                    buyInterface.showBuy(buyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

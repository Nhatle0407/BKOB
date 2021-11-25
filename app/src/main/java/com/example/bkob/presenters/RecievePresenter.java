package com.example.bkob.presenters;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.bkob.models.NotifyModel;
import com.example.bkob.views.adapters.NotifyAdapter;
import com.example.bkob.views.adapters.ReceiveAdapter;
import com.example.bkob.views.interfaces.NotifyInterface;
import com.example.bkob.views.interfaces.RecieveInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecievePresenter {
    private Context context;
    private RecieveInterface recieveInterface;
    private ArrayList<NotifyModel> notifyList;
    private ReceiveAdapter receiveAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public RecievePresenter(Context context, RecieveInterface recieveInterface) {
        this.context = context;
        this.recieveInterface = recieveInterface;
    }

    public void loadNotify(){
        notifyList = new ArrayList<>();
        DatabaseReference notifyRef = database.getReference("notifycations");
        notifyRef.child(FirebaseAuth.getInstance().getUid()).orderByChild("status").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                notifyList.clear();
                if(snapshot.hasChildren()){
                    for(DataSnapshot ds : snapshot.getChildren()){
                        NotifyModel notifyModel = ds.getValue(NotifyModel.class);
                        notifyList.add(notifyModel);
                    }
                    receiveAdapter = new ReceiveAdapter(notifyList);
                    recieveInterface.showRecieve(receiveAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

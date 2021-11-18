package com.example.bkob.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.bkob.R;
import com.example.bkob.databinding.FragmentChangeInfoBinding;
import com.example.bkob.databinding.FragmentReceiveBinding;
import com.example.bkob.models.SignUpModel;
import com.example.bkob.views.adapters.ReceiveAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ChangeInfoFragment extends Fragment {

    FragmentChangeInfoBinding binding;
    EditText editName, editPhoneNumber, editAddress;
    TextView userEmail;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    DatabaseReference databaseReference;
    DatabaseReference userRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangeInfoBinding.inflate(getLayoutInflater());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        userRef = databaseReference.child(firebaseUser.getUid());

        displayUserInfo();

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBackChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new AccountFragment());
            }
        });

        binding.btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserData(firebaseUser);
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainFragments, fragment)
                .commit();
    }

    private void displayUserInfo(){
        editName = binding.editName;
        editAddress = binding.editAddress;
        editPhoneNumber = binding.editPhoneNumber;
        userEmail = binding.userEmail;

        userEmail.setText(firebaseUser.getEmail());

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uid = ds.getKey();
                    String value = ds.getValue().toString();

                    switch (uid){
                        case "address":
                            editAddress.setText(value);
                            break;
                        case "name":
                            editName.setText(value);
                            break;
                        case "phone":
                            editPhoneNumber.setText(value);
                            break;
                        default:
                            break;
                    }
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("account", databaseError.getMessage());
            }
        });
    }

    private void changeUserData(FirebaseUser user) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", ""+ user.getUid());
        hashMap.put("name", ""+ editName.getText());
        hashMap.put("email", ""+ userEmail.getText());
        hashMap.put("phone", ""+ editPhoneNumber.getText());
        hashMap.put("address", ""+ editAddress.getText());
        hashMap.put("avatar", "");
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users");
        userRef.child(user.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                replaceFragment(new AccountFragment());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Thay đổi thất bại. Xin hãy thử lại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
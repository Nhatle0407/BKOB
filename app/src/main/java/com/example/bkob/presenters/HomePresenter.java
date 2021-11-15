package com.example.bkob.presenters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bkob.models.CategoryModel;
import com.example.bkob.views.adapters.CategoryAdapter;
import com.example.bkob.views.interfaces.HomeInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomePresenter {
    private Context context;
    private HomeInterface homeInterface;
    private ArrayList<CategoryModel> categoryList;
    private CategoryAdapter categoryAdapter;

    public HomePresenter(HomeInterface homeInterface, Context context) {
        this.homeInterface = homeInterface;
        this.context = context;
    }

    public void loadCategory(){
        categoryList = new ArrayList<>();
        DatabaseReference categoryRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("category");
        categoryRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    categoryList.clear();
                    DataSnapshot snapshot = task.getResult();
                    if(snapshot.hasChildren()){
                        for(DataSnapshot ds: snapshot.getChildren()){
                            CategoryModel categoryModel = ds.getValue(CategoryModel.class);
                            categoryList.add(categoryModel);
                        }
                    }
                    categoryAdapter = new CategoryAdapter(context, categoryList);
                    homeInterface.showCategory(categoryAdapter);

                }
                else{
                    Log.d("Category", "Cannot load category");
                }
            }
        });
    }
}

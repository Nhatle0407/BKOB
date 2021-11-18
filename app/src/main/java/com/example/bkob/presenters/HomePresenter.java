package com.example.bkob.presenters;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.bkob.models.BookModel;
import com.example.bkob.models.CategoryModel;
import com.example.bkob.views.adapters.BookAdapter;
import com.example.bkob.views.adapters.CategoryAdapter;
import com.example.bkob.views.interfaces.HomeInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomePresenter {
    private Context context;
    private HomeInterface homeInterface;
    private ArrayList<CategoryModel> categoryList;
    private CategoryAdapter categoryAdapter;
    private ArrayList<BookModel> bookList;
    private BookAdapter bookAdapter;
    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public HomePresenter(HomeInterface homeInterface, Context context) {
        this.homeInterface = homeInterface;
        this.context = context;
    }

    public void loadAllBook() {
        bookList = new ArrayList<>();
        DatabaseReference bookref = database.getReference("books");
        bookref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                if(snapshot.hasChildren()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        BookModel bookModel = ds.getValue(BookModel.class);
                        bookList.add(bookModel);
                    }
                    bookAdapter = new BookAdapter(context, bookList);
                    homeInterface.showAllBook(bookAdapter);
                }
                else {
                    homeInterface.emptyList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loadBookInCategory(String category){
        bookList = new ArrayList<>();
        DatabaseReference bookref = database.getReference("books");
        bookref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList.clear();
                if(snapshot.hasChildren()){
                    for (DataSnapshot ds : snapshot.getChildren()){
                        BookModel bookModel = ds.getValue(BookModel.class);
                        if(bookModel.getCategory().equals(category)){
                            bookList.add(bookModel);
                        }
                    }
                    bookAdapter = new BookAdapter(context, bookList);
                    homeInterface.showAllBook(bookAdapter);
                }
                else {
                    homeInterface.emptyList();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void loadCategory(){
        categoryList = new ArrayList<>();
        DatabaseReference categoryRef = database.getReference("category");
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

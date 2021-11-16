package com.example.bkob.presenters;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.bkob.R;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.CategoryModel;
import com.example.bkob.views.adapters.CategoryDropdownAdapter;
import com.example.bkob.views.interfaces.AddBookInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

public class AddBookPresenter {
    private Context context;
    private ArrayList<CategoryModel> categoryList;
    private CategoryDropdownAdapter categoryDropdownAdapter;
    private AddBookInterface addBookInterface;

    private FirebaseDatabase database = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/");

    public AddBookPresenter(Context context, AddBookInterface addBookInterface) {
        this.context = context;
        this.addBookInterface = addBookInterface;
    }

    public void getCategory(){
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
                    categoryDropdownAdapter = new CategoryDropdownAdapter(context, R.layout.spinner_selected, categoryList);
                    addBookInterface.setCategory(categoryDropdownAdapter);
                }
            }
        });
    }

    public void addBook(BookModel bookModel){
        if(!bookModel.haveImage()){
            addBookInterface.imageInvalid();
            return;
        }
        if(!bookModel.isValidName()){
            addBookInterface.nameInvalid();
            return;
        }
        if(!bookModel.isValidPrice()){
            addBookInterface.priceInvalid();
            return;
        }
        if(!bookModel.isValidQuantity()){
            addBookInterface.quatityInvalid();
            return;
        }

        String uid = FirebaseAuth.getInstance().getUid();
        String bookId = uid + System.currentTimeMillis();

        String filePath = "book_image/" + "" + bookId;
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePath);
        storageReference.putFile(bookModel.getImageUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isSuccessful()) ;
                Uri imageUrl = uriTask.getResult();

                if(uriTask.isSuccessful()){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("userId", ""+uid);
                    hashMap.put("name", ""+bookModel.getName());
                    hashMap.put("category", ""+bookModel.getCategory());
                    hashMap.put("price", ""+bookModel.getPrice());
                    hashMap.put("quantity", ""+bookModel.getQuantity());
                    hashMap.put("description", ""+bookModel.getDescription());
                    hashMap.put("imageUrl", ""+imageUrl);

                    DatabaseReference bookRef = database.getReference("books");
                    bookRef.child(bookId).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            addBookInterface.addBookSuccess();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            addBookInterface.addBookFail(e);
                        }
                    });
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                addBookInterface.addBookFail(e);
            }
        });
    }
}

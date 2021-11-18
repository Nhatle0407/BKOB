package com.example.bkob.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bkob.R;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.CategoryModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private Context context;
    public ArrayList<BookModel> bookList;

    public BookAdapter(Context context, ArrayList<BookModel> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        BookModel bookModel = bookList.get(position);

        String imageUrl = bookModel.getImageUrl();
        String name = bookModel.getName();
        String price = String.format("%,dđ", Integer.parseInt(bookModel.getPrice().toString()));

        holder.bookName.setText(name);
        holder.bookPrice.setText(price);
        try{
            Log.d("ALLBOOK", "Image Url: "+imageUrl);
            Picasso.get().load(imageUrl).into(holder.bookImage);
        }
        catch (Exception e){
            Log.d("ALLBOOK", "Fail to load image:"+e.getMessage());
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCard(bookModel);
            }
        });
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
                    Toast.makeText(context, "Sách đã có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                }
                else {
                    cartRef.child(uid).child(timeStamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Đã thêm vào giỏ!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder{
        private ImageView bookImage;
        private TextView bookName, bookPrice;
        private ImageButton btnAdd;

        public BookHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.avatar_item_search);
            bookName = itemView.findViewById(R.id.name_item_search);
            bookPrice = itemView.findViewById(R.id.price_item_search);
            btnAdd = itemView.findViewById(R.id.btn_cart_item_search);
        }
    }
}

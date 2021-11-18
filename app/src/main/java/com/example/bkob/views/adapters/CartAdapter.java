package com.example.bkob.views.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.models.BookModel;
import com.example.bkob.views.interfaces.CartInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder>{
    private List<BookModel> bookModels;

    public CartAdapter(List<BookModel> bookModels) {
        this.bookModels = bookModels;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        BookModel bookModel = bookModels.get(position);
        holder.name.setText(bookModel.getName());
        holder.price.setText(String.format("%,dđ", Integer.parseInt(bookModel.getPrice().toString())));
        try{
            Picasso.get().load(bookModel.getImageUrl()).into(holder.avatar);
        }
        catch (Exception e){
            Log.d("CART", "Fail to load image:"+e.getMessage());
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeBook(bookModel);
                bookModels.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    private void removeBook(BookModel bookModel) {
        DatabaseReference cartRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("carts");
        Query bookQuery = cartRef.child(FirebaseAuth.getInstance().getUid()).orderByChild("bookId").equalTo(bookModel.getBookId());


        bookQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    ds.getRef().removeValue();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    public String getTotal(){
        long total = 0;
        for(BookModel bookModel : bookModels){
            total+= Integer.parseInt(bookModel.getPrice());
        }
        return String.format("%,dđ", total);
    }

    class  CartHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, price, add, sub , quantity;
        ImageButton delete;
        public CartHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_cart);
            name = itemView.findViewById(R.id.name_cart);
            price = itemView.findViewById(R.id.price_cart);
            delete = itemView.findViewById(R.id.remove_cart);
        }
    }
}

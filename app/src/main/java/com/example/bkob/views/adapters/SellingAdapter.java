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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SellingAdapter extends RecyclerView.Adapter<SellingAdapter.SellingHolder> {

    private List<BookModel> bookModels;

    public SellingAdapter(List<BookModel> bookModel){
        this.bookModels = bookModel;
    }

    @NonNull
    @Override
    public SellingAdapter.SellingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_selling, parent, false);
        return new SellingAdapter.SellingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SellingAdapter.SellingHolder holder, int position) {
        BookModel bookModel = bookModels.get(position);
        holder.name.setText(bookModel.getName());
        holder.price.setText(String.format("%,dđ", Integer.parseInt(bookModel.getPrice().toString())));
        try{
            Picasso.get().load(bookModel.getImageUrl()).into(holder.avatar);
        }
        catch (Exception e){
            Log.d("SELLING", "Fail to load image:"+e.getMessage());
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

    @Override
    public int getItemCount() {
        return bookModels.size();
    }

    private void removeBook(BookModel bookModel) {
        DatabaseReference sellingRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("selling");
        Query bookQuery = sellingRef.child(FirebaseAuth.getInstance().getUid()).orderByChild("bookId").equalTo(bookModel.getBookId());

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

    public String getTotal(){
        long total = 0;
        for(BookModel bookModel : bookModels){
            total+= Integer.parseInt(bookModel.getPrice());
        }
        return String.format("%,dđ", total);
    }

    class SellingHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView name, price, add, sub , quantity;
        ImageButton delete;

        public SellingHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_item_selling);
            name = itemView.findViewById(R.id.name_item_selling);
            price = itemView.findViewById(R.id.price_item_selling);

            delete = itemView.findViewById(R.id.btn_remove_item_selling);
        }
    }
}

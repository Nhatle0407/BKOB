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
import com.squareup.picasso.Picasso;

import java.time.temporal.Temporal;
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
        holder.price.setText(bookModel.getPrice() + "đ");
        holder.quantity.setText(bookModel.getQuantity());
        try{
            Picasso.get().load(bookModel.getImageUrl()).into(holder.avatar);
        }
        catch (Exception e){
            Log.d("Book", "Fail to load image:"+e.getMessage());
        }
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookModels.remove(holder.getAdapterPosition());
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(bookModel.getQuantity()) + 1;
                bookModel.setQuantity( temp + "");
                holder.quantity.setText(bookModel.getQuantity() );
            }
        });
        holder.sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(bookModel.getQuantity()) - 1;
                bookModel.setQuantity( temp + "");
                holder.quantity.setText(bookModel.getQuantity());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookModels.size();
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
            sub = itemView.findViewById(R.id.cart_sub);
            add = itemView.findViewById(R.id.cart_add);
            quantity = itemView.findViewById(R.id.cart_quanty);
        }
    }
}

package com.example.bkob.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bkob.R;
import com.example.bkob.models.BookModel;
import com.example.bkob.models.CategoryModel;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
        String quantity = "Số lượng: " + bookModel.getQuantity();
        String price = String.format("%,dđ", Integer.parseInt(bookModel.getPrice().toString()));

        holder.bookName.setText(name);
        holder.bookQuantity.setText(quantity);
        holder.bookPrice.setText(price);
        try{
            Log.d("ALLBOOK", "Image Url: "+imageUrl);
            Picasso.get().load(imageUrl).into(holder.bookImage);
        }
        catch (Exception e){
            Log.d("ALLBOOK", "Fail to load image:"+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class BookHolder extends RecyclerView.ViewHolder{
        private ImageView bookImage;
        private TextView bookName, bookQuantity, bookPrice;

        public BookHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.avatar_item_search);
            bookName = itemView.findViewById(R.id.name_item_search);
            bookQuantity = itemView.findViewById(R.id.quanty_item_search);
            bookPrice = itemView.findViewById(R.id.price_item_search);
        }
    }
}

package com.example.bkob.views.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bkob.R;
import com.example.bkob.models.BuyModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BuyAdapter extends RecyclerView.Adapter<BuyAdapter.BuyHolder> {
    private ArrayList<BuyModel> buyModels;

    public BuyAdapter(ArrayList<BuyModel> buyModels) {
        this.buyModels = buyModels;
    }

    @NonNull
    @Override
    public BuyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_buy_history, parent, false);
        return new BuyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyHolder holder, int position) {
        BuyModel buyModel = buyModels.get(position);
        holder.name.setText(buyModel.getBookName());
        holder.price.setText(String.format("%,dđ", Integer.parseInt(buyModel.getPrice().toString())));
        holder.date.setText(buyModel.getDate());
        try{
            Picasso.get().load(buyModel.getBookImage()).into(holder.bookImage);
        }
        catch (Exception e){
            Log.d("BUY", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return buyModels.size();
    }

    class BuyHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView name, price, date;
        public BuyHolder(@NonNull View itemView) {
            super(itemView);

            bookImage = itemView.findViewById(R.id.im_book_buy);
            name = itemView.findViewById(R.id.name_buy);
            price = itemView.findViewById(R.id.price_buy);
            date = itemView.findViewById(R.id.date_buy);
        }
    }
}

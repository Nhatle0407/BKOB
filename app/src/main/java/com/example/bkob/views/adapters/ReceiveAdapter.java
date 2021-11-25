package com.example.bkob.views.adapters;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.views.interfaces.DetailSaleInterface;
import com.example.bkob.views.interfaces.OrderRInterface;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReceiveAdapter extends  RecyclerView.Adapter<ReceiveAdapter.ReceiveHolder>{
    private final List<NotifyModel> modelList;
    private OrderRInterface orderRInterface;

    public ReceiveAdapter(List<NotifyModel> receiveModelList) {
        this.modelList = receiveModelList;
    }

    @NonNull
    @Override
    public ReceiveHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_receive, parent, false);
        return new ReceiveAdapter.ReceiveHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReceiveHolder holder, int position) {
        NotifyModel item = modelList.get(position);
        holder.name.setText(item.getBookName());
        holder.price.setText(String.format("%,dđ", Integer.parseInt(item.getTotal().toString())));
        holder.date.setText(item.getDate());
        holder.from.setText("Từ: " + item.getFromName());
        Log.d("RECIEVE", ""+item.getBookImage());
        try {
            Picasso.get().load(item.getBookImage()).into(holder.avatar);
        }
        catch (Exception e){
            Log.d("RECIEVE", e.getMessage());
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderRInterface != null){
                    orderRInterface.showDetail(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void onClick(OrderRInterface orderRInterface){
        this.orderRInterface = orderRInterface;
    }

    class ReceiveHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, from, date, price, detail;
        RelativeLayout item;
        public ReceiveHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_receive);
            name = itemView.findViewById(R.id.name_receive1);
            price = itemView.findViewById(R.id.price_receive);
            item = itemView.findViewById(R.id.item_recieve);
            from = itemView.findViewById(R.id.from_receive);
            date = itemView.findViewById(R.id.date_receive);
        }
    }
}

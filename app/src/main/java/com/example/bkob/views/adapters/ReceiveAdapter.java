package com.example.bkob.views.adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.models.ReceiveModel;
import com.example.bkob.views.interfaces.DetailSaleInterface;

import java.util.List;

public class ReceiveAdapter extends  RecyclerView.Adapter<ReceiveAdapter.ReceiveHolder>{
    private final List<NotifyModel> modelList;
    private DetailSaleInterface detailSaleInterface;

    public ReceiveAdapter(List<NotifyModel> receiveModelList, DetailSaleInterface detailSaleInterface) {
        this.modelList = receiveModelList;
        this.detailSaleInterface = detailSaleInterface;
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
        holder.price.setText(item.getTotal());
        holder.date.setText(item.getDate());
        holder.detail.setText(Html.fromHtml("<u>"+"Chi tiết"+"</u>"));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(detailSaleInterface != null){
                    detailSaleInterface.detailSale(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    class ReceiveHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView name, from, date, price, detail;
        public ReceiveHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar_receive);
            name = itemView.findViewById(R.id.name_receive1);
            price = itemView.findViewById(R.id.price_receive);
            detail = itemView.findViewById(R.id.detail_receive);
            from = itemView.findViewById(R.id.from_receive);
            date = itemView.findViewById(R.id.date_receive);
        }
    }
}

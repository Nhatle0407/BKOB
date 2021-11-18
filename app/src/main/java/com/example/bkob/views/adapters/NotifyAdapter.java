package com.example.bkob.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bkob.R;
import com.example.bkob.models.NotifyModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyHolder>{
    private Context context;
    private ArrayList<NotifyModel> notifyList;

    public NotifyAdapter(Context context, ArrayList<NotifyModel> notifyList) {
        this.context = context;
        this.notifyList = notifyList;
    }

    @NonNull
    @Override

    public NotifyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new NotifyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyHolder holder, int position) {
        NotifyModel notifyModel = notifyList.get(position);

        String notify = "Bạn đã nhận được yêu cầu mua \"" + notifyModel.getBookName() + "\" từ \"" + notifyModel.getFromName() + "\"";
        holder.notify.setText(notify);
        holder.date.setText(notifyModel.getDate());
        if(notifyModel.getStatus().equals("read")){
            holder.point.setVisibility(View.GONE);
            holder.itemView.setBackgroundResource(R.drawable.background_body_color);
        }
        else{
            holder.point.setVisibility(View.VISIBLE);
            holder.itemView.setBackgroundResource(R.drawable.background_receive);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    class NotifyHolder extends RecyclerView.ViewHolder {
        private ImageView point;
        private TextView notify, viewOrder, date;

        public NotifyHolder(@NonNull View itemView) {
            super(itemView);

            point = itemView.findViewById(R.id.point_unread);
            notify = itemView.findViewById(R.id.tv_notify);
            viewOrder = itemView.findViewById(R.id.tv_viewOrder);
            date = itemView.findViewById(R.id.tv_notify_date);
        }
    }
}

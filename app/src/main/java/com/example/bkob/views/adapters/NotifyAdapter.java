package com.example.bkob.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.bkob.R;
import com.example.bkob.models.NotifyModel;
import com.example.bkob.views.interfaces.OrderRInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyHolder>{
    private Context context;
    private ArrayList<NotifyModel> notifyList;
    private OrderRInterface orderRInterface;

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

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference notifyRef = FirebaseDatabase.getInstance("https://bkob-a0229-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("notifycations");
                Log.d("ORDER", ""+notifyModel.getNotifyId());
                notifyRef.child(FirebaseAuth.getInstance().getUid()).child(notifyModel.getNotifyId()).child("status").setValue("read").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
                if(orderRInterface != null){
                    orderRInterface.showDetail(notifyModel);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    public void onClick(OrderRInterface orderRInterface){
        this.orderRInterface = orderRInterface;
    }

    class NotifyHolder extends RecyclerView.ViewHolder {
        private ImageView point;
        private TextView notify, viewOrder, date;
        private RelativeLayout item;

        public NotifyHolder(@NonNull View itemView) {
            super(itemView);

            point = itemView.findViewById(R.id.point_unread);
            notify = itemView.findViewById(R.id.tv_notify);
            viewOrder = itemView.findViewById(R.id.tv_viewOrder);
            date = itemView.findViewById(R.id.tv_notify_date);
            item = itemView.findViewById(R.id.item_notify);
        }
    }
}

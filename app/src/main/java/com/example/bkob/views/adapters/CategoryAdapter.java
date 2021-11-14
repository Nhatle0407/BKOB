package com.example.bkob.views.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bkob.R;
import com.example.bkob.models.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.HolderCategory> {
    private Context context;
    public ArrayList<CategoryModel> categoryList;


    public CategoryAdapter(Context context, ArrayList<CategoryModel> categoryList){
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public HolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new HolderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategory holder, int position) {
        CategoryModel categoryModel = categoryList.get(position);

        String name = categoryModel.getName();
        String imageUrl = categoryModel.getImageUrl();

        holder.categoryName.setText(name);
        try{
            Picasso.get().load(imageUrl).into(holder.categoryImage);
        }
        catch (Exception e){
            Log.d("Category", "Fail to load image:"+e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class HolderCategory extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryName;

        public HolderCategory(@NonNull View itemView) {
            super(itemView);

            categoryImage = itemView.findViewById(R.id.img_category);
            categoryName = itemView.findViewById(R.id.tv_categoryName);
        }
    }
}

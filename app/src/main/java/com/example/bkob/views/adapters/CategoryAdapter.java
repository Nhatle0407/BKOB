package com.example.bkob.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bkob.R;
import com.example.bkob.models.Category;

import java.util.List;


public class CategoryAdapter extends ArrayAdapter<Category> {

    public CategoryAdapter(@NonNull Context context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_spinner_selected);

        Category category = this.getItem(position);
        if(category != null){
            tvSelected.setText(category.getTitle());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView tvSpinner = convertView.findViewById(R.id.spinner_tv);

        Category category = this.getItem(position);
        if(category != null){
            tvSpinner.setText(category.getTitle());
        }
        return convertView;
    }
}

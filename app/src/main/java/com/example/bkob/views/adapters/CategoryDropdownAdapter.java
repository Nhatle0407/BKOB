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
import com.example.bkob.models.CategoryModel;

import java.util.List;


public class CategoryDropdownAdapter extends ArrayAdapter<CategoryModel> {

    public CategoryDropdownAdapter(@NonNull Context context, int resource, @NonNull List<CategoryModel> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_selected, parent, false);
        TextView tvSelected = convertView.findViewById(R.id.tv_spinner_selected);

        CategoryModel categoryModel = this.getItem(position);
        if(categoryModel != null){
            tvSelected.setText(categoryModel.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_item, parent, false);
        TextView tvSpinner = convertView.findViewById(R.id.spinner_tv);

        CategoryModel categoryModel = this.getItem(position);
        if(categoryModel != null){
            tvSpinner.setText(categoryModel.getName());
        }
        return convertView;
    }
}

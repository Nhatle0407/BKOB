package com.example.bkob.presenters;

import android.widget.Filter;

import com.example.bkob.models.BookModel;
import com.example.bkob.views.adapters.BookAdapter;

import java.util.ArrayList;

public class BookFilter extends Filter {
    private BookAdapter bookAdapter;
    private ArrayList<BookModel> filterList;

    public BookFilter(BookAdapter bookAdapter, ArrayList<BookModel> filterList) {
        this.bookAdapter = bookAdapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults filterResults = new FilterResults();
        if(constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();
            ArrayList<BookModel> filteredBook = new ArrayList<>();
            for( int i = 0; i < filterList.size(); i++){
                if(filterList.get(i).getName().toUpperCase().contains(constraint)
                || filterList.get(i).getCategory().toUpperCase().contains(constraint)){
                    filteredBook.add(filterList.get(i));
                }
            }
            filterResults.count = filteredBook.size();
            filterResults.values = filteredBook;
        } else {
            filterResults.count = filterList.size();
            filterResults.values = filterList;
        }
        return filterResults;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        bookAdapter.bookList = (ArrayList<BookModel>) results.values;
        bookAdapter.notifyDataSetChanged();
    }
}

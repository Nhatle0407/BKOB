package com.example.bkob.Singleton;

import com.example.bkob.models.BookModel;

import java.util.ArrayList;
import java.util.List;

public class CartSingleton {
    private static List<BookModel> bookModels;

    public static List<BookModel> getBookModels() {
        if(bookModels == null) bookModels = new ArrayList<>();
        return bookModels;
    }
}

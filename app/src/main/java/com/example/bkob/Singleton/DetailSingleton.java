package com.example.bkob.Singleton;

import com.example.bkob.models.BookModel;

public class DetailSingleton {
    private  static BookModel bookModel = null;

    public static BookModel getBookModel() {
        return bookModel;
    }

    public static void setBookModel(BookModel bookModel) {
        DetailSingleton.bookModel = bookModel;
    }

}

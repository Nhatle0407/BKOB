package com.example.bkob.views.interfaces;

import com.example.bkob.views.adapters.CategoryDropdownAdapter;

public interface AddBookInterface {
    void setCategory(CategoryDropdownAdapter adapter);
    void nameInvalid();
    void priceInvalid();
    void quatityInvalid();
    void imageInvalid();
    void addBookSuccess();
    void addBookFail(Exception e);
}

package com.example.bkob.models;

import java.util.Date;

public class ReceiveModel {
    private BookModel item;
    private String date;
    private UserModel userModel;

    public ReceiveModel(BookModel item, String date, UserModel userModel) {
        this.item = item;
        this.date = date;
        this.userModel = userModel;
    }

    public BookModel getItem() {
        return item;
    }

    public void setItem(BookModel item) {
        this.item = item;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}

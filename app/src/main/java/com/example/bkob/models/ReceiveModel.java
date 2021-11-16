package com.example.bkob.models;

import java.util.Date;

public class ReceiveModel {
    private BookModel item;
    private String date;
    private String userid;

    public ReceiveModel(BookModel item, String date, String userid) {
        this.item = item;
        this.date = date;
        this.userid = userid;
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

    public String getUserModel() {
        return userid;
    }

    public void setUserModel(String userid) {
        this.userid = userid;
    }
}

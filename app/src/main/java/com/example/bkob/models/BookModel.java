package com.example.bkob.models;

import android.net.Uri;
import android.text.TextUtils;

public class BookModel {
    private String bookId, userId, name, description, category, price, imageUrl;
    private Uri imageUri;

    public BookModel() {
    }

    public BookModel(String name, String description, String category, String price, String userId, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }
    public BookModel(String name, String description, String category, String price, Uri imageUri) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.imageUri = imageUri;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public String getBookId() {
        return bookId;
    }

    public String getUid() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUid(String uid) {
        this.userId = uid;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }


    public boolean isValidName(){
        return !TextUtils.isEmpty(name);
    }


    public boolean isValidPrice(){
        return !TextUtils.isEmpty(price);
    }

    public boolean haveImage(){
        return (imageUri!=null);
    }


}

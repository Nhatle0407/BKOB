package com.example.bkob.models;

import android.net.Uri;
import android.text.TextUtils;

public class BookModel {


    private String name, description, category, price, quantity, uid, imageUrl;
    private Uri imageUri;

    public String getUid() {
        return uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public BookModel(String name, String description, String category, String price, String quantity, Uri imageUri) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.imageUri = imageUri;
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

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public BookModel(String name, String description, String category, String price, String quantity, String uid, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.uid = uid;
        this.imageUrl = imageUrl;
    }
    public boolean isValidName(){
        return !TextUtils.isEmpty(name);
    }


    public boolean isValidQuantity(){
        return !TextUtils.isEmpty(quantity);
    }

    public boolean isValidPrice(){
        return !TextUtils.isEmpty(price);
    }

    public boolean haveImage(){
        return (imageUri!=null);
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

    public String getQuantity() {
        return quantity;
    }
}

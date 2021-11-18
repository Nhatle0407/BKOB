package com.example.bkob.models;

public class UserModel {
    private  String email, name, address, phone, userId, avatar;

    public UserModel() {
    }

    public UserModel(String mail, String name, String address, String phone, String id) {
        this.email = mail;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.userId = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getUserId() {
        return userId;
    }

    public String getAvatar() {
        return avatar;
    }
}

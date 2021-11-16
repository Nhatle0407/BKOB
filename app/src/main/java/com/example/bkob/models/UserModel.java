package com.example.bkob.models;

public class UserModel {
    private  String mail, name, address, phone, pass, userid;

    public UserModel(String mail, String name, String address, String phone, String pass, String id) {
        this.mail = mail;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.pass = pass;
        this.userid = id;
    }

    public String getId() {
        return userid;
    }

    public void setId(String id) {
        this.userid = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}

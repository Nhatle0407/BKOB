package com.example.bkob.models;

public class CategoryModel {
    private String name;
    private String imageUrl;

    public CategoryModel(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public CategoryModel() {
    }

    public CategoryModel(String title) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

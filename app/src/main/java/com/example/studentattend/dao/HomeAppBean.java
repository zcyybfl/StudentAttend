package com.example.studentattend.dao;

public class HomeAppBean {

    private String name;
    private String ImageUrl;

    public HomeAppBean(String name, String imageUrl) {
        this.name = name;
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}

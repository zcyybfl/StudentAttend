package com.example.studentattend.dao;

public class MyBean {

    private String name;

    private String data;

    private int imageId;

    public MyBean(String name, String data, int imageId) {
        this.name = name;
        this.data = data;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

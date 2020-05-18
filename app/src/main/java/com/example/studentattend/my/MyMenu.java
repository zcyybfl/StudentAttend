package com.example.studentattend.my;

public class MyMenu {

    private String name;

    private String data;

    private int imageId;

    public MyMenu(String name, String data,int imageId) {
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

package com.example.studentattend.dao;

import java.util.Date;

public class AttendStudentBean {

    private String time;

    private String status;

    public AttendStudentBean(String time, String status) {
        this.time = time;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

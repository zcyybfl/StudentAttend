package com.example.studentattend.dao;

import java.util.Date;

public class AttendBean {

    private Date time;

    private boolean status;

    public AttendBean(Date time, boolean status) {
        this.time = time;
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

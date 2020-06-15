package com.example.studentattend.dao;

public class AttendTeacherBean {

    private String studentId;
    private String studentName;
    private String attendance;
    private String attendId;

    public AttendTeacherBean(String studentId, String studentName, String attendance, String attendId) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendance = attendance;
        this.attendId = attendId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }
}

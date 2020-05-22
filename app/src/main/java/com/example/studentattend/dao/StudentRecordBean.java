package com.example.studentattend.dao;

public class StudentRecordBean {

    private String courseId;
    private String courseName;
    private int attendance;
    private int absenceFromDuty;

    public StudentRecordBean(String courseId,String courseName, int attendance, int absenceFromDuty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.attendance = attendance;
        this.absenceFromDuty = absenceFromDuty;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public int getAbsenceFromDuty() {
        return absenceFromDuty;
    }

    public void setAbsenceFromDuty(int absenceFromDuty) {
        this.absenceFromDuty = absenceFromDuty;
    }
}

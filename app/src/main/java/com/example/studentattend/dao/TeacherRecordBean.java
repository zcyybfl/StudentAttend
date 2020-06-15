package com.example.studentattend.dao;

public class TeacherRecordBean {

    private String courseId;
    private String classId;
    private String time;
    private String courseName;
    private int attendance;
    private int absenceFromDuty;

    public TeacherRecordBean(String courseId, String classId, String time, String courseName, int attendance, int absenceFromDuty) {
        this.courseId = courseId;
        this.classId = classId;
        this.time = time;
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

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

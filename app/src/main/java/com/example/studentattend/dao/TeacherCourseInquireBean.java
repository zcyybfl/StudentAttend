package com.example.studentattend.dao;

public class TeacherCourseInquireBean {

    private String teacherId;
    private String courseId;
    private String courseName;

    public TeacherCourseInquireBean(String teacherId, String courseId, String courseName) {
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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
}

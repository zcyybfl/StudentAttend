package com.example.studentattend.dao;

public class ClassCourseInquireBean {

    private String classId;
    private String teacherId;
    private String courseId;

    public ClassCourseInquireBean(String classId, String teacherId, String courseId) {
        this.classId = classId;
        this.teacherId = teacherId;
        this.courseId = courseId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
}

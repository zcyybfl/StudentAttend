package com.example.studentattend.dao;

public class StudentOrTeacherInquireBean {

    private String studentOrTeacherId;
    private String studentOrTeacherName;
    private String studentOrTeacherSex;
    private String classIdOrDepartment;

    public StudentOrTeacherInquireBean(String studentOrTeacherId, String studentOrTeacherName, String studentOrTeacherSex, String classIdOrDepartment) {
        this.studentOrTeacherId = studentOrTeacherId;
        this.studentOrTeacherName = studentOrTeacherName;
        this.studentOrTeacherSex = studentOrTeacherSex;
        this.classIdOrDepartment = classIdOrDepartment;
    }

    public String getStudentOrTeacherId() {
        return studentOrTeacherId;
    }

    public void setStudentOrTeacherId(String studentOrTeacherId) {
        this.studentOrTeacherId = studentOrTeacherId;
    }

    public String getStudentOrTeacherName() {
        return studentOrTeacherName;
    }

    public void setStudentOrTeacherName(String studentOrTeacherName) {
        this.studentOrTeacherName = studentOrTeacherName;
    }

    public String getStudentOrTeacherSex() {
        return studentOrTeacherSex;
    }

    public void setStudentOrTeacherSex(String studentOrTeacherSex) {
        this.studentOrTeacherSex = studentOrTeacherSex;
    }

    public String getClassIdOrDepartment() {
        return classIdOrDepartment;
    }

    public void setClassIdOrDepartment(String classIdOrDepartment) {
        this.classIdOrDepartment = classIdOrDepartment;
    }
}

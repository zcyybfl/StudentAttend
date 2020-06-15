package com.example.studentattend.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentattend.R;
import com.example.studentattend.dao.StudentOrTeacherInquireBean;

import java.util.List;

public class StudentOrTeacherInquireAdapter extends ArrayAdapter<StudentOrTeacherInquireBean> {

    private int resource;

    public StudentOrTeacherInquireAdapter(@NonNull Context context, int resource, @NonNull List<StudentOrTeacherInquireBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StudentOrTeacherInquireBean studentOrTeacherInquireBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView studentOrTeacherId = view.findViewById(R.id.student_or_teacher_id);
        TextView studentOrTeacherName = view.findViewById(R.id.student_or_teacher_name);
        TextView studentOrTeacherSex = view.findViewById(R.id.student_or_teacher_sex);
        TextView classIdOrCourseId = view.findViewById(R.id.class_id_or_course_id);
        assert studentOrTeacherInquireBean != null;
        studentOrTeacherId.setText(studentOrTeacherInquireBean.getStudentOrTeacherId());
        studentOrTeacherName.setText(studentOrTeacherInquireBean.getStudentOrTeacherName());
        studentOrTeacherSex.setText(studentOrTeacherInquireBean.getStudentOrTeacherSex());
        classIdOrCourseId.setText(studentOrTeacherInquireBean.getClassIdOrCourseId());
        return view;
    }
    //覆盖父类ArrayAdapter<>的getView，让Adapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

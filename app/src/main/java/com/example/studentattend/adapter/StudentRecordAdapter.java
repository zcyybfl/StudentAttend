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
import com.example.studentattend.dao.StudentRecordBean;

import java.util.List;

public class StudentRecordAdapter extends ArrayAdapter<StudentRecordBean> {

    private int resource;

    public StudentRecordAdapter(@NonNull Context context, int resource, @NonNull List<StudentRecordBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        StudentRecordBean studentRecordBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView courseName = view.findViewById(R.id.course_name);
        TextView attendance = view.findViewById(R.id.attendance);
        TextView absenceFromDuty = view.findViewById(R.id.absence_from_duty);
        assert studentRecordBean != null;
        courseName.setText(studentRecordBean.getCourseName());
        attendance.setText(String.valueOf(studentRecordBean.getAttendance()));
        absenceFromDuty.setText(String.valueOf(studentRecordBean.getAbsenceFromDuty()));
        return view;
    }
    //覆盖父类ArrayAdapter<Fruit>的getView，让FruitAdapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

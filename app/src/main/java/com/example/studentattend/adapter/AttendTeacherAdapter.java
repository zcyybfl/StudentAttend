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
import com.example.studentattend.dao.AttendTeacherBean;

import java.util.List;

public class AttendTeacherAdapter extends ArrayAdapter<AttendTeacherBean> {

    private int resource;

    public AttendTeacherAdapter(@NonNull Context context, int resource, @NonNull List<AttendTeacherBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       AttendTeacherBean attendTeacherBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView teacherOrder = view.findViewById(R.id.teacher_order);
        TextView studentId = view.findViewById(R.id.student_id);
        TextView studentName = view.findViewById(R.id.student_name);
        TextView teacherAttendanceStatus = view.findViewById(R.id.teacher_attendance_status);
        assert attendTeacherBean != null;
        teacherOrder.setText(String.valueOf(position + 1));
        studentId.setText(attendTeacherBean.getStudentId());
        studentName.setText(attendTeacherBean.getStudentName());
        teacherAttendanceStatus.setText(attendTeacherBean.getAttendance());
        return view;
    }
    //覆盖父类ArrayAdapter<>的getView，让Adapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

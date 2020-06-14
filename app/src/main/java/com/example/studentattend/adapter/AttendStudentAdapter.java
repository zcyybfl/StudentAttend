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
import com.example.studentattend.dao.AttendStudentBean;

import java.util.List;

public class AttendStudentAdapter extends ArrayAdapter<AttendStudentBean> {

    private int resource;

    public AttendStudentAdapter(@NonNull Context context, int resource, @NonNull List<AttendStudentBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        AttendStudentBean attendStudentBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView studentOrder = view.findViewById(R.id.student_order);
        TextView studentDate = view.findViewById(R.id.student_date);
        TextView studentAttendanceStatus = view.findViewById(R.id.student_attendance_status);
        assert attendStudentBean != null;
        studentOrder.setText(String.valueOf(position + 1));
        studentDate.setText(attendStudentBean.getTime());
        studentAttendanceStatus.setText(attendStudentBean.getStatus());
        return view;
    }
    //覆盖父类ArrayAdapter<>的getView，让Adapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

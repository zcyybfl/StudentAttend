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
import com.example.studentattend.dao.TeacherRecordBean;

import java.util.List;

public class TeacherRecordAdapter extends ArrayAdapter<TeacherRecordBean> {

    private int resource;

    public TeacherRecordAdapter(@NonNull Context context, int resource, @NonNull List<TeacherRecordBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TeacherRecordBean teacherRecordBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView teacherOrder = view.findViewById(R.id.teacher_order);
        TextView teacherDate = view.findViewById(R.id.teacher_date);
        TextView teacherAttendance = view.findViewById(R.id.teacher_attendance);
        TextView teacherAbsenceFromDuty = view.findViewById(R.id.teacher_absence_from_duty);
        assert teacherRecordBean != null;
        teacherOrder.setText(String.valueOf(position + 1));
        teacherDate.setText(teacherRecordBean.getTime());
        teacherAttendance.setText(String.valueOf(teacherRecordBean.getAttendance()));
        teacherAbsenceFromDuty.setText(String.valueOf(teacherRecordBean.getAbsenceFromDuty()));
        return view;
    }
    //覆盖父类ArrayAdapter<Fruit>的getView，让FruitAdapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

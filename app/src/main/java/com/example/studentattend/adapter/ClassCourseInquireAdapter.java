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
import com.example.studentattend.dao.ClassCourseInquireBean;

import java.util.List;

public class ClassCourseInquireAdapter extends ArrayAdapter<ClassCourseInquireBean> {

    private int resource;

    public ClassCourseInquireAdapter(@NonNull Context context, int resource, @NonNull List<ClassCourseInquireBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ClassCourseInquireBean classCourseInquireBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView classId = view.findViewById(R.id.class_id);
        TextView teacherId = view.findViewById(R.id.class_teacher_id);
        TextView courseId = view.findViewById(R.id.class_course_id);
        assert classCourseInquireBean != null;
        classId.setText(classCourseInquireBean.getClassId());
        teacherId.setText(classCourseInquireBean.getTeacherId());
        courseId.setText(classCourseInquireBean.getCourseId());
        return view;
    }
    //覆盖父类ArrayAdapter<>的getView，让Adapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

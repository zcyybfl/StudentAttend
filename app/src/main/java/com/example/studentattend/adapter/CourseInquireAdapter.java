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
import com.example.studentattend.dao.CourseInquireBean;

import java.util.List;

public class CourseInquireAdapter extends ArrayAdapter<CourseInquireBean> {

    private int resource;


    public CourseInquireAdapter(@NonNull Context context, int resource, @NonNull List<CourseInquireBean> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CourseInquireBean courseInquireBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        //resource就是item的布局
        //LayoutInflater.from(getContext()).inflate(resource,parent,false)的作用是：从布局xml生成控件
        TextView courseId = view.findViewById(R.id.course_id);
        TextView courseName = view.findViewById(R.id.course_name);
        assert courseInquireBean != null;
        courseId.setText(courseInquireBean.getCourseId());
        courseName.setText(courseInquireBean.getCourseName());
        return view;
    }
    //覆盖父类ArrayAdapter<>的getView，让Adapter能够按照resourceId的样子，
    //显示objects的内容在listView上
}

package com.example.studentattend.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.adapter.CourseInquireAdapter;
import com.example.studentattend.dao.CourseInquireBean;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment implements View.OnClickListener {

    private List<CourseInquireBean> courseInquireBeans = new ArrayList<>();
    private View view;
    private Button addCourse;
    private Button deleteCourse;
    private Button selectCourse;
    private Button modifyCourse;
    private TextView nullCourse;
    private ListView courseInquireListView;
    private Boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_course,container,false);
        findViewById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addCourse.setOnClickListener(this);
        deleteCourse.setOnClickListener(this);
        selectCourse.setOnClickListener(this);
        modifyCourse.setOnClickListener(this);
    }

    private void findViewById() {
        addCourse = view.findViewById(R.id.add_course);
        deleteCourse = view.findViewById(R.id.delete_course);
        selectCourse = view.findViewById(R.id.select_course);
        modifyCourse = view.findViewById(R.id.modify_course);
        nullCourse = view.findViewById(R.id.null_course);
        courseInquireListView = view.findViewById(R.id.course_inquire_listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_course:
                break;
            case R.id.delete_course:
                break;
            case R.id.select_course:
                selectCourseDialog();
                break;
            case R.id.modify_course:
                break;
            default:
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void selectCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View selectCourseView = inflater.inflate(R.layout.select_dialog, null);
        final EditText selectCourse = selectCourseView.findViewById(R.id.inquire);
        selectCourse.setHint("请输入课程号(模糊查询)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("课程查询");
        builder.setView(selectCourseView);
        builder.setPositiveButton("查询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectCourse.getText().toString().isEmpty()) {
                    //首先会查询所有
                    flag = selectAll();
                } else {
                    //把课程号信息传递给数据库
                    flag = select(selectCourse.getText().toString());
                }
                //如果返回的信息没有课程，将nullStudent设置为显示,反之，设置为隐藏
                initListView();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void initListView() {
        if (flag) {
            nullCourse.setVisibility(View.GONE);
            initView();
        } else {
            nullCourse.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        initCourseInquireBean();
        initCourseInquireAdapter();
    }

    private void initCourseInquireBean() {
        courseInquireBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    CourseInquireBean one = new CourseInquireBean("C001", "Java程序设计");
                    courseInquireBeans.add(one);
                    CourseInquireBean two = new CourseInquireBean("C002", "数据结构");
                    courseInquireBeans.add(two);
                }
            }
        }).start();
    }

    private void initCourseInquireAdapter() {
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        CourseInquireAdapter courseInquireAdapter = new CourseInquireAdapter(requireContext(),
                R.layout.admin_fragment_course_item,courseInquireBeans);
        courseInquireListView.setAdapter(courseInquireAdapter);
    }

    private boolean selectAll() {
        //查询所有

        //查询成功与否，要替换成
        return true;
    }

    private boolean select(String id) {
        //传递给数据库


        //查询成功与否，要替换成
        return true;
    }
}

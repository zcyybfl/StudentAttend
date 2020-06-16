package com.example.studentattend.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.adapter.CourseInquireAdapter;
import com.example.studentattend.dao.CourseInquireBean;
import com.example.studentattend.service.ServiceAdminSearchCourseInfo;

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
                addCourseDialog();
                break;
            case R.id.delete_course:
                deleteCourseDialog();
                break;
            case R.id.select_course:
                selectCourseDialog();
                break;
            case R.id.modify_course:
                modifyCourseDialog();
                break;
            default:
                break;
        }
    }

    private void addCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View addCourseView = inflater.inflate(R.layout.two_input_dialog, null);
        final EditText courseId = addCourseView.findViewById(R.id.one);
        final EditText courseName = addCourseView.findViewById(R.id.two);
        courseId.setHint("请输入课程号");
        courseName.setHint("请输入课程名");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("添加课程");
        builder.setView(addCourseView);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (courseId.getText().toString().isEmpty() || courseName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"课程号和课程名不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //查询该课程存在不
                    flag = addSelect(courseId.getText().toString());
                    if (flag) {
                        Toast.makeText(getContext(),"添加失败,该课程号已存在",Toast.LENGTH_SHORT).show();
                    } else {
                        //添加课程
                        addCourse(courseId.getText().toString(),courseName.getText().toString());
                        Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void deleteCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View deleteCourseView = inflater.inflate(R.layout.one_input_dialog, null);
        final EditText courseId = deleteCourseView.findViewById(R.id.one);
        courseId.setHint("请输入课程号");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("删除课程");
        builder.setView(deleteCourseView);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (courseId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"课程号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //判断课程存在不
                    flag = deleteSelect(courseId.getText().toString());
                    if (flag) {
                        //删除课程
                        deleteCourse(courseId.getText().toString());
                        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),"删除失败,该课程号不存在",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void selectCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View selectCourseView = inflater.inflate(R.layout.one_input_dialog, null);
        final EditText courseId = selectCourseView.findViewById(R.id.one);
        courseId.setHint("请输入课程号(模糊查询)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("查询课程");
        builder.setView(selectCourseView);
        builder.setPositiveButton("查询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把课程号信息传递给数据库
                flag = select(courseId.getText().toString());
                //如果返回的信息没有课程，将nullStudent设置为显示,反之，设置为隐藏
                initListView();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void modifyCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View modifyCourseView = inflater.inflate(R.layout.two_input_dialog, null);
        final EditText courseId = modifyCourseView.findViewById(R.id.one);
        final EditText courseName = modifyCourseView.findViewById(R.id.two);
        courseId.setHint("请输入课程号");
        courseName.setHint("请输入修改课程名");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("修改课程");
        builder.setView(modifyCourseView);
        builder.setPositiveButton("修改", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (courseId.getText().toString().isEmpty() || courseName.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"课程号和课程名不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //判断课程存在不
                    flag = modifySelect(courseId.getText().toString());
                    if (flag) {
                        //修改课程
                        modifyCourse(courseId.getText().toString(),courseName.getText().toString());
                        Toast.makeText(getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(),"修改失败,该课程号不存在",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private boolean addSelect(String courseId) {
        //查询添加课程号存在否
        return false;
    }

    private void addCourse(String courseId,String courseName) {
        //添加课程
    }

    private boolean deleteSelect(String courseId) {
        //查询删除课程号存在否
        return true;
    }

    private void deleteCourse(String courseId) {
        //删除课程
    }

    private boolean modifySelect(String courseId) {
        //查询修改课程号存在否
        return true;
    }

    private void modifyCourse(String courseId,String courseName) {
        //修改课程
    }

    private boolean select(String courseId) {
        //传递给数据库
        ServiceAdminSearchCourseInfo serviceAdminSearchCourseInfo = new ServiceAdminSearchCourseInfo();
        serviceAdminSearchCourseInfo.init(courseId,"course");
        serviceAdminSearchCourseInfo.start();
        courseInquireBeans = serviceAdminSearchCourseInfo.courseInfo();
        //查询有无数据
        if (courseInquireBeans.isEmpty()){
            return false;
        }else {
            return true;
        }

    }

    private void initListView() {
        if (flag) {
            nullCourse.setVisibility(View.GONE);
            courseInquireListView.setVisibility(View.VISIBLE);
            initView();
        } else {
            nullCourse.setVisibility(View.VISIBLE);
            courseInquireListView.setVisibility(View.GONE);
        }
    }

    private void initView() {
        //initCourseInquireBean();
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
}

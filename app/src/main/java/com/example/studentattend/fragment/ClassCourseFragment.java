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
import com.example.studentattend.adapter.ClassCourseInquireAdapter;
import com.example.studentattend.dao.ClassCourseInquireBean;

import java.util.ArrayList;
import java.util.List;

public class ClassCourseFragment extends Fragment implements View.OnClickListener {

    private List<ClassCourseInquireBean> classCourseInquireBeans = new ArrayList<>();
    private View view;
    private Button addClassCourse;
    private Button deleteClassCourse;
    private Button selectClassCourse;
    private TextView nullClassCourse;
    private ListView classCourseInquireListView;
    private Boolean flag = true;
    private boolean judge1 = false;
    private boolean judge2 = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_class_course,container,false);
        findViewById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addClassCourse.setOnClickListener(this);
        deleteClassCourse.setOnClickListener(this);
        selectClassCourse.setOnClickListener(this);
    }

    private void findViewById() {
        addClassCourse = view.findViewById(R.id.add_class_course);
        deleteClassCourse = view.findViewById(R.id.delete_class_course);
        selectClassCourse = view.findViewById(R.id.select_class_course);
        nullClassCourse = view.findViewById(R.id.null_class_course);
        classCourseInquireListView = view.findViewById(R.id.class_course_inquire_listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_class_course:
                addClassCourseDialog();
                break;
            case R.id.delete_class_course:
                deleteClassCourseDialog();
                break;
            case R.id.select_class_course:
                selectClassCourseDialog();
                break;
            default:
                break;
        }
    }

    private void addClassCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View addClassCourseView = inflater.inflate(R.layout.two_input_dialog, null);
        final EditText classId = addClassCourseView.findViewById(R.id.one);
        final EditText teacherId = addClassCourseView.findViewById(R.id.two);
        classId.setHint("请输入班级号");
        teacherId.setHint("请输入教工号");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("添加班级课程");
        builder.setView(addClassCourseView);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (classId.getText().toString().isEmpty() || teacherId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"班级号和教工号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //查询该班级，教师存在不,查询教师存在课程不，查询班级有这这门课不
                    flag = addSelectClassCourse(classId.getText().toString(),teacherId.getText().toString());
                    if (flag) {
                        if (judge1) {
                            if (judge2) {
                                Toast.makeText(getContext(),"添加失败,该班级已存在该课程",Toast.LENGTH_SHORT).show();
                            } else {
                                addClassCourse(classId.getText().toString(),teacherId.getText().toString());
                                Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(),"添加失败,该教师没有课程",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(),"添加失败,班级号或教工号不存在",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void deleteClassCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View deleteClassCourseView = inflater.inflate(R.layout.two_input_dialog, null);
        final EditText classId = deleteClassCourseView.findViewById(R.id.one);
        final EditText teacherId = deleteClassCourseView.findViewById(R.id.two);
        classId.setHint("请输入班级号");
        teacherId.setHint("请输入教工号");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("删除班级课程");
        builder.setView(deleteClassCourseView);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (classId.getText().toString().isEmpty() || teacherId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"班级号和教工号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    //查询班级存在不，查询该班级存在这课程不
                    flag = deleteSelectClassCourse(classId.getText().toString(),teacherId.getText().toString());
                    if (flag) {
                        if (judge1) {
                            //删除课程
                            deleteClassCourse(classId.getText().toString(),teacherId.getText().toString());
                            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"删除失败,该班级不存在该课程",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(),"删除失败,班级号不存在",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void selectClassCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View selectClassCourseView = inflater.inflate(R.layout.one_input_dialog, null);
        final EditText classId = selectClassCourseView.findViewById(R.id.one);
        classId.setHint("请输入班级号(模糊查询)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("查询班级课程");
        builder.setView(selectClassCourseView);
        builder.setPositiveButton("查询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把班级号信息传递给数据库
                flag = select(classId.getText().toString());
                //如果返回的信息没有课程，将nullStudent设置为显示,反之，设置为隐藏
                initListView();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private boolean addSelectClassCourse(String classId,String teacherId) {
        //查询该班级，教师存在不,查询教师存在课程不，查询班级有这这门课不
        //查询教师存在课程不
        judge1 = true;
        //查询班级有这这门课不
        judge2 = false;
        return true;
    }

    private void addClassCourse(String classId,String teacherId) {
        //添加课程
    }

    private boolean deleteSelectClassCourse(String classId,String teacherId) {
        //查询班级存在不，查询该班级存在这课程不
        //查询该班级存在这课程不
        judge1 = true;
        return true;
    }

    private void deleteClassCourse(String classId,String teacherId) {
        //删除课程
    }

    private boolean select(String classId) {
        //传递给数据库
        //查询有无数据
        return true;
    }

    private void initListView() {
        if (flag) {
            nullClassCourse.setVisibility(View.GONE);
            initView();
        } else {
            nullClassCourse.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        initClassCourseInquireBean();
        initClassCourseInquireAdapter();
    }

    private void initClassCourseInquireBean() {
        classCourseInquireBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    ClassCourseInquireBean one = new ClassCourseInquireBean("18406199","1840610610","C001");
                    classCourseInquireBeans.add(one);
                    ClassCourseInquireBean two = new ClassCourseInquireBean("18406199","1840610608","C002");
                    classCourseInquireBeans.add(two);
                }
            }
        }).start();
    }

    private void initClassCourseInquireAdapter() {
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        ClassCourseInquireAdapter classCourseInquireAdapter = new ClassCourseInquireAdapter(
                requireContext(),R.layout.admin_fragment_class_course_item,classCourseInquireBeans);
        classCourseInquireListView.setAdapter(classCourseInquireAdapter);
    }
}

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
import com.example.studentattend.adapter.TeacherCourseInquireAdapter;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.TeacherCourseInquireBean;
import com.example.studentattend.service.ServiceAdminAddCurseInfo;
import com.example.studentattend.service.ServiceAdminDeleteCourseInfo;
import com.example.studentattend.service.ServiceAdminSearchCourseInfo;

import java.util.ArrayList;
import java.util.List;

public class TeacherCourseFragment extends Fragment implements View.OnClickListener {

    private List<TeacherCourseInquireBean> teacherCourseInquireBeans = new ArrayList<>();
    private View view;
    private Button addTeacherCourse;
    private Button deleteTeacherCourse;
    private Button selectTeacherCourse;
    private TextView nullTeacherCourse;
    private ListView teacherCourseInquireListView;
    private Boolean flag = true;
    //判断老师存在课程不
    private boolean judge = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_teacher_course,container,false);
        findViewById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addTeacherCourse.setOnClickListener(this);
        deleteTeacherCourse.setOnClickListener(this);
        selectTeacherCourse.setOnClickListener(this);
    }

    private void findViewById() {
        addTeacherCourse = view.findViewById(R.id.add_teacher_course);
        deleteTeacherCourse = view.findViewById(R.id.delete_teacher_course);
        selectTeacherCourse = view.findViewById(R.id.select_teacher_course);
        nullTeacherCourse = view.findViewById(R.id.null_teacher_course);
        teacherCourseInquireListView = view.findViewById(R.id.teacher_course_inquire_listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_teacher_course:
                addTeacherCourseDialog();
                break;
            case R.id.delete_teacher_course:
                deleteTeacherCourseDialog();
                break;
            case R.id.select_teacher_course:
                selectTeacherCourseDialog();
                break;
            default:
                break;
        }
    }

    private void addTeacherCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View addTeacherCourseView = inflater.inflate(R.layout.two_input_dialog, null);
        final EditText teacherId = addTeacherCourseView.findViewById(R.id.one);
        final EditText courseId = addTeacherCourseView.findViewById(R.id.two);
        teacherId.setHint("请输入教工号");
        courseId.setHint("请输入课程号");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("添加教师课程");
        builder.setView(addTeacherCourseView);
        builder.setPositiveButton("添加", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (teacherId.getText().toString().isEmpty() || courseId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"教工号和课程号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    addTeacherCourse(teacherId.getText().toString(),courseId.getText().toString());
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void deleteTeacherCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View deleteTeacherCourseView = inflater.inflate(R.layout.one_input_dialog, null);
        final EditText teacherId = deleteTeacherCourseView.findViewById(R.id.one);
        teacherId.setHint("请输入教工号");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("删除教师课程");
        builder.setView(deleteTeacherCourseView);
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (teacherId.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),"教工号不能为空",Toast.LENGTH_SHORT).show();
                } else {
                    deleteTeacherCourse(teacherId.getText().toString());
//                    //查询教师存在不和有无课程
//                    flag = deleteSelectTeacherCourse(teacherId.getText().toString());
//                    if (flag) {
//                        deleteTeacherCourse(teacherId.getText().toString());
//                        Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(getContext(),"删除失败,教工号不存在或该教工不存在课程",Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void selectTeacherCourseDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        final View selectTeacherCourseView = inflater.inflate(R.layout.one_input_dialog, null);
        final EditText teacherId = selectTeacherCourseView.findViewById(R.id.one);
        teacherId.setHint("请输入教工号(模糊查询)");
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("查询教师课程");
        builder.setView(selectTeacherCourseView);
        builder.setPositiveButton("查询", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把课程号信息传递给数据库
                flag = select(teacherId.getText().toString());
                //如果返回的信息没有课程，将nullStudent设置为显示,反之，设置为隐藏
                initListView();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.setCancelable(false);
        builder.show();
    }

    private void addTeacherCourse(String teacherId,String courseId) {
        //添加教工课程
        ServiceAdminAddCurseInfo serviceAdminAddCurseInfo = new ServiceAdminAddCurseInfo();
        serviceAdminAddCurseInfo.init(teacherId,courseId,"teacher");
        serviceAdminAddCurseInfo.start();
        BaseBean baseBean = serviceAdminAddCurseInfo.show();
        if (baseBean.getMsg().equals("添加成功")){
            Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"添加失败",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deleteSelectTeacherCourse(String teacherId) {
        //查询教师存在不和有无课程
        return true;
    }

    private void deleteTeacherCourse(String teacherId) {
        //删除教工课程
        ServiceAdminDeleteCourseInfo serviceAdminDeleteCourseInfo = new ServiceAdminDeleteCourseInfo();
        serviceAdminDeleteCourseInfo.init(teacherId,"","teacher");
        serviceAdminDeleteCourseInfo.start();
        BaseBean baseBean = serviceAdminDeleteCourseInfo.show();
        if (baseBean.getMsg().equals("删除成功")){
            Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getContext(),"删除失败",Toast.LENGTH_SHORT).show();
        }
    }

    private boolean select(String teacherId) {
        //传递给数据库
        ServiceAdminSearchCourseInfo serviceAdminSearchCourseInfo = new ServiceAdminSearchCourseInfo();
        serviceAdminSearchCourseInfo.init(teacherId,"teacher");
        serviceAdminSearchCourseInfo.start();
        teacherCourseInquireBeans = serviceAdminSearchCourseInfo.teacherCourseInfo();
        //查询有无数据
        return !teacherCourseInquireBeans.isEmpty();
    }

    private void initListView() {
        if (flag) {
            nullTeacherCourse.setVisibility(View.GONE);
            teacherCourseInquireListView.setVisibility(View.VISIBLE);
            initTeacherCourseInquireAdapter();
        } else {
            nullTeacherCourse.setVisibility(View.VISIBLE);
            teacherCourseInquireListView.setVisibility(View.GONE);
        }
    }

    private void initTeacherCourseInquireAdapter() {
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        TeacherCourseInquireAdapter teacherCourseInquireAdapter = new TeacherCourseInquireAdapter(
                requireContext(),R.layout.admin_fragment_teacher_course_item,teacherCourseInquireBeans);
        teacherCourseInquireListView.setAdapter(teacherCourseInquireAdapter);
    }
}

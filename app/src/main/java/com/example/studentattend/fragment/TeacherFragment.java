package com.example.studentattend.fragment;

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
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.adapter.StudentOrTeacherInquireAdapter;
import com.example.studentattend.dao.StudentOrTeacherInquireBean;
import com.example.studentattend.service.ServiceAdminSearchStudentOrTeacherInfo;

import java.util.ArrayList;
import java.util.List;

public class TeacherFragment extends Fragment implements View.OnClickListener {

    private List<StudentOrTeacherInquireBean> studentOrTeacherInquireBeans = new ArrayList<>();
    private View view;
    private EditText teacherInquire;
    private Button teacherInquireButton;
    private TextView nullTeacher;
    private ListView teacherInquireListView;
    private Boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_teacher,container,false);
        findViewById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        teacherInquireButton.setOnClickListener(this);
    }

    private void findViewById() {
        teacherInquire = view.findViewById(R.id.teacher_inquire);
        teacherInquireButton = view.findViewById(R.id.teacher_inquire_button);
        nullTeacher = view.findViewById(R.id.null_teacher);
        teacherInquireListView = view.findViewById(R.id.teacher_inquire_listView);
    }

    private void initView() {
        //initStudentOrTeacherInquireBean();
        initStudentOrTeacherInquireAdapter();
    }

    private void initStudentOrTeacherInquireAdapter() {
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        StudentOrTeacherInquireAdapter studentOrTeacherInquireAdapter = new StudentOrTeacherInquireAdapter(requireContext(),
                R.layout.admin_fragment_student_and_teacher_item,studentOrTeacherInquireBeans);
        teacherInquireListView.setAdapter(studentOrTeacherInquireAdapter);
    }

    private void initStudentOrTeacherInquireBean() {
        studentOrTeacherInquireBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
                    StudentOrTeacherInquireBean one = new StudentOrTeacherInquireBean("1840610610","黄思捷",
                            "男","计算机系");
                    studentOrTeacherInquireBeans.add(one);
                    StudentOrTeacherInquireBean two = new StudentOrTeacherInquireBean("1840610608","郑龙涛",
                            "女","计算机系");
                    studentOrTeacherInquireBeans.add(two);
                    StudentOrTeacherInquireBean three = new StudentOrTeacherInquireBean("1840610626","向前程",
                            "男","计算机系");
                    studentOrTeacherInquireBeans.add(three);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.teacher_inquire_button) {
            //把教工号信息传递给数据库
            flag = select(teacherInquire.getText().toString());
            //如果返回的信息没有教师，将nullTeacher置为显示,反之，设置为隐藏
            initListView();
        }
    }

    private void initListView() {
        if (flag) {
            nullTeacher.setVisibility(View.GONE);
            teacherInquireListView.setVisibility(View.VISIBLE);
            initView();
        } else {
            nullTeacher.setVisibility(View.VISIBLE);
            teacherInquireListView.setVisibility(View.GONE);
        }
    }

    private boolean select(String teacherId) {
        //传递给数据库
        ServiceAdminSearchStudentOrTeacherInfo serviceAdminSearchStudentOrTeacherInfo = new ServiceAdminSearchStudentOrTeacherInfo();
        serviceAdminSearchStudentOrTeacherInfo.init(teacherId,"teacher");
        serviceAdminSearchStudentOrTeacherInfo.start();
        studentOrTeacherInquireBeans = serviceAdminSearchStudentOrTeacherInfo.show();
        //查询成功与否，要替换成
        if (studentOrTeacherInquireBeans.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}

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

import java.util.ArrayList;
import java.util.List;

public class StudentFragment extends Fragment implements View.OnClickListener {

    private List<StudentOrTeacherInquireBean> studentOrTeacherInquireBeans = new ArrayList<>();
    private View view;
    private EditText studentInquire;
    private Button studentInquireButton;
    private TextView nullStudent;
    private ListView studentInquireListView;
    private Boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.admin_fragment_student,container,false);
        findViewById();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        studentInquireButton.setOnClickListener(this);
    }

    private void findViewById() {
        studentInquire = view.findViewById(R.id.student_inquire);
        studentInquireButton = view.findViewById(R.id.student_inquire_button);
        nullStudent = view.findViewById(R.id.null_student);
        studentInquireListView = view.findViewById(R.id.student_inquire_listView);
    }

    private void initView() {
        initStudentOrTeacherInquireBean();
        initStudentOrTeacherInquireAdapter();
    }

    private void initStudentOrTeacherInquireAdapter() {
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        StudentOrTeacherInquireAdapter studentOrTeacherInquireAdapter = new StudentOrTeacherInquireAdapter(requireContext(),
                R.layout.admin_fragment_student_and_teacher_item,studentOrTeacherInquireBeans);
        studentInquireListView.setAdapter(studentOrTeacherInquireAdapter);
    }

    private void initStudentOrTeacherInquireBean() {
        studentOrTeacherInquireBeans.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
                    StudentOrTeacherInquireBean one = new StudentOrTeacherInquireBean("1840610610","黄思捷",
                            "男","18406199");
                    studentOrTeacherInquireBeans.add(one);
                    StudentOrTeacherInquireBean two = new StudentOrTeacherInquireBean("1840610608","郑龙涛",
                            "女","18406199");
                    studentOrTeacherInquireBeans.add(two);
                    StudentOrTeacherInquireBean three = new StudentOrTeacherInquireBean("1840610626","向前程",
                            "男","18406199");
                    studentOrTeacherInquireBeans.add(three);
                }
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.student_inquire_button) {
            if (studentInquire.getText().toString().isEmpty()) {
                //首先会查询所有
                flag = selectAll();
            } else {
                //把信息传递给数据库
                flag = select(studentInquire.getText().toString());

            }
            //如果返回的信息没有学生，将nullStudent设置为显示,反之，设置为隐藏
            initListView();
        }
    }

    private void initListView() {
        if (flag) {
            nullStudent.setVisibility(View.GONE);
            initView();
        } else {
            nullStudent.setVisibility(View.VISIBLE);
        }
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

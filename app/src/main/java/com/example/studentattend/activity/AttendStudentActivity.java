package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentattend.R;
import com.example.studentattend.adapter.AttendStudentAdapter;
import com.example.studentattend.dao.AttendStudentBean;

import java.util.ArrayList;
import java.util.List;

public class AttendStudentActivity extends AppCompatActivity implements View.OnClickListener {

    private static String obtainCourseId;
    private static String obtainCourseName;
    private List<AttendStudentBean> attendStudentBeanList = new ArrayList<>();

    public static final int INFORMATION = 1;
    TextView courseName;
    TextView courseId;
    TextView teacherName;
    TextView teacherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_student);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        obtainCourseId = bundle.getString("courseId");
        obtainCourseName = bundle.getString("courseName");
        information();
        initAttend();
        ListView listView = findViewById(R.id.student_listView);
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        AttendStudentAdapter attendStudentAdapter = new AttendStudentAdapter(
                AttendStudentActivity.this,R.layout.about_student_attend_item,attendStudentBeanList);
        listView.setAdapter(attendStudentAdapter);
        TextView recordReturnStudent = findViewById(R.id.record_return_student);
        courseName = findViewById(R.id.course_name_text);
        courseId = findViewById(R.id.course_id_text);
        teacherName = findViewById(R.id.teacher_name_text);
        teacherId = findViewById(R.id.teacher_id_text);
        recordReturnStudent.setOnClickListener(this);
    }

    private void initAttend() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 10;i++) {
                    AttendStudentBean attendStudentBean = new AttendStudentBean("2019/10/26 10:49","签到");
                    attendStudentBeanList.add(attendStudentBean);
                }
            }
        }).start();
    }

    private void information() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = INFORMATION;
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == INFORMATION) {
                courseName.setText(obtainCourseName);
                courseId.setText(obtainCourseId);
                teacherName.setText(SplashActivity.userBean.getName());
                teacherId.setText(SplashActivity.userBean.getSno());
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.record_return_student) {
            finish();
        }
    }
}
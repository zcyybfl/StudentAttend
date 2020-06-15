package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentattend.R;
import com.example.studentattend.adapter.StudentRecordAdapter;
import com.example.studentattend.dao.StudentRecordBean;
import com.example.studentattend.service.ServiceAttendRecordStudent;

import java.util.ArrayList;
import java.util.List;

public class AttendRecordStudentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private List<StudentRecordBean> studentRecordBeanList = new ArrayList<>();

    public static final int INFORMATION = 1;

    TextView studentName;
    TextView studentId;
    TextView studentPhone;
    TextView studentEmail;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_record_student);
        ListView listView = findViewById(R.id.student_listView);
        TextView nullRecord = findViewById(R.id.null_record);
        studentRecordBeanList.clear();
        if (flag) {
            initStudentRecordBean();
        }else {
            nullRecord.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        StudentRecordAdapter studentRecordAdapter = new StudentRecordAdapter(
                AttendRecordStudentActivity.this,R.layout.about_student_item,studentRecordBeanList);
        listView.setAdapter(studentRecordAdapter);
        listView.setOnItemClickListener(AttendRecordStudentActivity.this);
        TextView recordReturnStudent = findViewById(R.id.record_return_student);
        studentName = findViewById(R.id.student_name_text);
        studentId = findViewById(R.id.student_id_text);
        studentPhone = findViewById(R.id.student_phone_text);
        studentEmail = findViewById(R.id.student_email_text);
        information();
        recordReturnStudent.setOnClickListener(this);
    }

    private void initStudentRecordBean() {
        ServiceAttendRecordStudent serviceAttendRecordStudent = new ServiceAttendRecordStudent();
        serviceAttendRecordStudent.init(SplashActivity.userBean.getSno());
        serviceAttendRecordStudent.start();
        studentRecordBeanList = serviceAttendRecordStudent.show();
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
                studentName.setText(SplashActivity.userBean.getName());
                studentId.setText(SplashActivity.userBean.getSno());
                studentPhone.setText(SplashActivity.userBean.getPhone());
                studentEmail.setText(SplashActivity.userBean.getEmail());
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.record_return_student) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent attendStudentActivity = new Intent(this,AttendStudentActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("courseId",studentRecordBeanList.get(position).getCourseId());
        bundle.putString("courseName",studentRecordBeanList.get(position).getCourseName());
        bundle.putString("teacherId",studentRecordBeanList.get(position).getTeacherId());
        bundle.putString("teacherName",studentRecordBeanList.get(position).getTeacherName());
        attendStudentActivity.putExtras(bundle);
        startActivity(attendStudentActivity);
    }
}
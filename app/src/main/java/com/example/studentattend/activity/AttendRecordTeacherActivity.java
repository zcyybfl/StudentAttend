package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentattend.R;
import com.example.studentattend.adapter.TeacherRecordAdapter;
import com.example.studentattend.dao.TeacherRecordBean;
import com.example.studentattend.service.ServiceAttendRecordTeacher;
import com.example.studentattend.service.ServiceTeacherInfo;

import java.util.ArrayList;
import java.util.List;

public class AttendRecordTeacherActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private List<TeacherRecordBean> teacherRecordBeanList = new ArrayList<>();
    public static final int CHOICE = 1;
    public static final int INFORMATION = 2;
    TextView nullRecord;
    TextView teacherNull;
    TextView teacherName;
    TextView teacherId;
    TextView courseName;
    TextView courseId;
    ListView listView;
    String course_name;
    String course_id;
    Button choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_record_teacher);
        //先获取老师教的课程
        information();
        listView = findViewById(R.id.teacher_listView);
        listView.setOnItemClickListener(this);
        nullRecord = findViewById(R.id.null_record);
        teacherNull = findViewById(R.id.teacher_null_course);
        TextView recordReturnTeacher = findViewById(R.id.record_return_teacher);
        choice = findViewById(R.id.choice);
        choice.setOnClickListener(this);
        recordReturnTeacher.setOnClickListener(this);
        teacherName = findViewById(R.id.teacher_name_text);
        teacherId = findViewById(R.id.teacher_id_text);
        courseName = findViewById(R.id.course_name_text);
        courseId = findViewById(R.id.course_id_text);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.record_return_teacher:
                finish();
                break;
            case R.id.choice:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = CHOICE;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == CHOICE) {
                final EditText editText = new EditText(AttendRecordTeacherActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT);
                new AlertDialog.Builder(AttendRecordTeacherActivity.this)
                        .setTitle("请输入班级号")
                        .setIcon(R.mipmap.app_icon)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ServiceAttendRecordTeacher serviceAttendRecordTeacher = new ServiceAttendRecordTeacher();
                                serviceAttendRecordTeacher.init(SplashActivity.userBean.getSno(),editText.getText().toString());
                                serviceAttendRecordTeacher.start();
                                teacherRecordBeanList = serviceAttendRecordTeacher.show();
                                if (teacherRecordBeanList.isEmpty()){
                                    listView.setVisibility(View.GONE);
                                    nullRecord.setVisibility(View.VISIBLE);
                                }else {
                                    listView.setVisibility(View.VISIBLE);
                                    nullRecord.setVisibility(View.GONE);
                                    TeacherRecordAdapter teacherRecordAdapter = new TeacherRecordAdapter(
                                            AttendRecordTeacherActivity.this, R.layout.about_teacher_item,teacherRecordBeanList);
                                    listView.setAdapter(teacherRecordAdapter);
                                }
                            }
                        })
                        .setNegativeButton("取消",null)
                        .setCancelable(false)
                        .show();
            }else if (msg.what == INFORMATION) {
                ServiceTeacherInfo serviceTeacherInfo = new ServiceTeacherInfo();
                serviceTeacherInfo.init(SplashActivity.userBean.getSno());
                serviceTeacherInfo.start();
                TeacherRecordBean teacherRecordBean = serviceTeacherInfo.show();
                course_name = teacherRecordBean.getCourseName();
                course_id = teacherRecordBean.getCourseId();
                teacherName.setText(SplashActivity.userBean.getName());
                teacherId.setText(SplashActivity.userBean.getSno());
                courseName.setText(course_name);
                courseId.setText(course_id);
                if (course_name.isEmpty() || course_id.isEmpty()) {
                    teacherNull.setVisibility(View.VISIBLE);
                    choice.setVisibility(View.GONE);
                }
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent attendTeacherActivity = new Intent(this,AttendTeacherActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("time",teacherRecordBeanList.get(position).getTime());
        bundle.putString("classId",teacherRecordBeanList.get(position).getClassId());
        bundle.putString("courseName",course_name);
        bundle.putString("courseId",course_id);
        attendTeacherActivity.putExtras(bundle);
        startActivity(attendTeacherActivity);
    }
}
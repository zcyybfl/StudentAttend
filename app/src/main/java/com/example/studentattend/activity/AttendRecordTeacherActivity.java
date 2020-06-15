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

import java.util.ArrayList;
import java.util.List;

public class AttendRecordTeacherActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private List<TeacherRecordBean> teacherRecordBeanList = new ArrayList<>();
    public static final int CHOICE = 1;
    public static final int INFORMATION = 2;
    int flag;
    TextView nullRecord;
    TextView teacherName;
    TextView teacherId;
    TextView courseName;
    TextView courseId;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_record_teacher);
        //先获取老师教的课程
        //。。。。。
        information();
        listView = findViewById(R.id.teacher_listView);
        listView.setOnItemClickListener(this);
        nullRecord = findViewById(R.id.null_record);
        TextView recordReturnTeacher = findViewById(R.id.record_return_teacher);
        Button choice = findViewById(R.id.choice);
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

    private void initTeacherRecordBean() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (flag == 1) {
                    for (int i = 0;i < 10;i++) {
                        TeacherRecordBean teacherRecordBean = new TeacherRecordBean("C1001","18406199",
                                "2020/6/23 12:00:00","数据结构",100,0);
                        teacherRecordBeanList.add(teacherRecordBean);
                    }
                } else if (flag == 2){
                    for (int i = 0;i < 10;i++) {
                        TeacherRecordBean teacherRecordBean = new TeacherRecordBean("C1002","18406188",
                                "2020/5/23 12:00:00",   "数据结构",10,0);
                        teacherRecordBeanList.add(teacherRecordBean);
                    }
                }
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
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == CHOICE) {
                final EditText editText = new EditText(AttendRecordTeacherActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                new AlertDialog.Builder(AttendRecordTeacherActivity.this)
                        .setTitle("请输入班级号")
                        .setIcon(R.mipmap.app_icon)
                        .setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //服务端交互
                                //测试代码,判断有无数据
                                if (editText.getText().toString().equals("18406199")) {
                                    flag = 1;
                                    nullRecord.setVisibility(View.GONE);

                                } else if (editText.getText().toString().equals("18406188")){
                                    flag = 2;
                                    nullRecord.setVisibility(View.GONE);
                                } else {
                                    nullRecord.setVisibility(View.VISIBLE);
                                }
                                teacherRecordBeanList.clear();
                                initTeacherRecordBean();
                                TeacherRecordAdapter teacherRecordAdapter = new TeacherRecordAdapter(
                                        AttendRecordTeacherActivity.this, R.layout.about_teacher_item,teacherRecordBeanList);
                                listView.setAdapter(teacherRecordAdapter);
                                teacherRecordAdapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .setCancelable(false)
                        .show();
            }else if (msg.what == INFORMATION) {
                teacherName.setText(SplashActivity.userBean.getName());
                teacherId.setText(SplashActivity.userBean.getSno());
                courseName.setText("数据结构");
                courseId.setText("C001");
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent attendTeacherActivity = new Intent(this,AttendTeacherActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("time",teacherRecordBeanList.get(position).getTime());
        bundle.putString("classId",teacherRecordBeanList.get(position).getClassId());
        bundle.putString("courseName",teacherRecordBeanList.get(position).getCourseName());
        bundle.putString("courseId",teacherRecordBeanList.get(position).getCourseId());
        attendTeacherActivity.putExtras(bundle);
        startActivity(attendTeacherActivity);
    }
}
package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.studentattend.R;
import com.example.studentattend.adapter.AttendTeacherAdapter;
import com.example.studentattend.dao.AttendTeacherBean;

import java.util.ArrayList;
import java.util.List;

public class AttendTeacherActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private static String time;
    private static String classId;
    private static String courseName;
    private static String courseId;
    public static final int INFORMATION = 1;
    public static final int MODIFY = 2;
    private List<AttendTeacherBean> attendTeacherBeanList = new ArrayList<>();
    TextView courseNameText;
    TextView courseIdText;
    TextView timeText;
    TextView classIdText;
    AttendTeacherAdapter attendTeacherAdapter;
    private static String studentId;
    private static String status;
    private static boolean flag;
    private static int position1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_teacher);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        time = bundle.getString("time");
        classId = bundle.getString("classId");
        courseName = bundle.getString("courseName");
        courseId = bundle.getString("courseId");
        information();
        initAttend();
        ListView listView = findViewById(R.id.teacher_listView);
        attendTeacherAdapter = new AttendTeacherAdapter(
                AttendTeacherActivity.this,R.layout.about_teacher_attend_item,attendTeacherBeanList);
        listView.setAdapter(attendTeacherAdapter);
        TextView recordReturnTeacher = findViewById(R.id.record_return_teacher);
        recordReturnTeacher.setOnClickListener(this);
        courseNameText = findViewById(R.id.course_name_text);
        courseIdText = findViewById(R.id.course_id_text);
        timeText = findViewById(R.id.time_text);
        classIdText = findViewById(R.id.class_id_text);
        listView.setOnItemClickListener(this);
    }

    private void initAttend() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i < 6;i++) {
                    AttendTeacherBean attendTeacherBean = new AttendTeacherBean("1840610610","黄思捷","签到");
                    attendTeacherBeanList.add(attendTeacherBean);
                    AttendTeacherBean attendTeacherBean2 = new AttendTeacherBean("1840610608","郑龙涛","未签");
                    attendTeacherBeanList.add(attendTeacherBean2);
                    AttendTeacherBean attendTeacherBean3 = new AttendTeacherBean("1840610626","向前程","迟签");
                    attendTeacherBeanList.add(attendTeacherBean3);
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
                courseNameText.setText(courseName);
                classIdText.setText(classId);
                timeText.setText(time);
                courseIdText.setText(courseId);
            } else if (msg.what == MODIFY) {
                new AlertDialog.Builder(AttendTeacherActivity.this)
                        .setTitle("请选择")
                        .setIcon(R.mipmap.app_icon)
                        .setSingleChoiceItems(new String[]{"签到", "迟签", "未签"}, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:status = "签到";break;
                                    case 1:status = "迟签";break;
                                    case 2:status = "未签";break;
                                    default:break;
                                }
                                new AlertDialog.Builder(AttendTeacherActivity.this)
                                        .setTitle("提示")
                                        .setMessage("确定修改签到状况")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //更新服务器数据

                                                //跟新客户端数据
                                                flag = true;
                                                attendTeacherBeanList.get(position1).setStatus(status);
                                                attendTeacherAdapter.notifyDataSetChanged();
                                                flag = false;
                                            }
                                        })
                                        .setNegativeButton("取消",null)
                                        .setCancelable(false)
                                        .show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消",null)
                        .setCancelable(false)
                        .show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.record_return_teacher) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        studentId = attendTeacherBeanList.get(position).getStudentId();
        position1 = position;
        modify();
    }

    //修改签到情况
    private void modify() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = MODIFY;
                handler.sendMessage(message);
            }
        }).start();
    }
}
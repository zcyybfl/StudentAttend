package com.example.studentattend.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.studentattend.R;

public class MobileNumber extends BaseActivity implements View.OnClickListener {

    private TextView phone;
    private String tel;
    public static final int UPDATE_UI = 1;
    public static boolean student_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        student_teacher = bundle.getBoolean("student_teacher");
        TextView numberReturn = findViewById(R.id.number_return);
        phone = findViewById(R.id.phone);
        Button bindMobile = findViewById(R.id.bind_mobile);
        telJudge();
        numberReturn.setOnClickListener(this);
        bindMobile.setOnClickListener(this);
    }

    private void telJudge() {
        tel = SplashActivity.userBean.getPhone();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = UPDATE_UI;
                handler.sendMessage(message);
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                phone.setText(tel);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number_return:
                finish();
                break;
            case R.id.bind_mobile:
                Intent bindMobileNumber = new Intent(this,BindMobileNumberActivity.class);
                //用Bundle携带数据
                Bundle bundle=new Bundle();
                bundle.putBoolean("student_teacher",student_teacher);
                bindMobileNumber.putExtras(bundle);
                startActivity(bindMobileNumber);
                break;
            default:
                break;
        }
    }
}
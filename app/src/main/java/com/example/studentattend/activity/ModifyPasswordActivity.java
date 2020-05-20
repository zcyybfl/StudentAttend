package com.example.studentattend.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.studentattend.R;
import com.example.studentattend.collector.ActivityCollector;

public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private TextView error;
    public static final int UPDATE_UI = 1;
    public static final int UPDATE_Activity = 2;
    public static boolean student_teacher;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        student_teacher = bundle.getBoolean("student_teacher");
        TextView modifyPasswordReturn = findViewById(R.id.modify_return);
        oldPassword = findViewById(R.id.old_password);
        newPassword = findViewById(R.id.new_password);
        newPasswordAgain = findViewById(R.id.new_password_again);
        error = findViewById(R.id.error_password);
        Button submit = findViewById(R.id.submit);
        modifyPasswordReturn.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private boolean judge() {
        if (oldPassword.getText().toString().equals("")
                || newPassword.getText().toString().equals("")
                || newPasswordAgain.getText().toString().equals("")) {
            error.setText("密码不能为空");
            reset();
            return false;
        } else if (!oldPassword.getText().toString().equals(password)) {
            error.setText("原密码不正确");
            reset();
            return false;
        } else if (!newPassword.getText().toString().equals(newPasswordAgain.getText().toString())) {
            error.setText("两次输入密码不相同");
            reset();
            return false;
        } else if (newPassword.getText().toString().equals(oldPassword.getText().toString())) {
            error.setText("新密码不能和旧密码相同");
            reset();
            return false;
        } else {
            return true;
        }
    }

    private void reset() {
        oldPassword.setText("");
        newPassword.setText("");
        newPasswordAgain.setText("");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = UPDATE_UI;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == UPDATE_UI) {
                error.setText("");
            }else if (msg.what == UPDATE_Activity) {
                new AlertDialog.Builder(ModifyPasswordActivity.this)
                        .setTitle("修改成功")
                        .setMessage("登录已失效，请重新登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent loginActivity = new Intent(ModifyPasswordActivity.this,LoginActivity.class);
                                startActivity(loginActivity);
                                ActivityCollector.finishAll(false);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.modify_return:
                finish();
                break;
            case R.id.submit:
                if (student_teacher) {
                    password = LoginActivity.studentBean.getPassword();
                } else {
//                    password = LoginActivity.teacherBean.getPassword();
                }
                if (judge()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = UPDATE_Activity;
                            handler.sendMessage(message);
                        }
                    }).start();
                }
                break;
            default:
                break;
        }
    }

}
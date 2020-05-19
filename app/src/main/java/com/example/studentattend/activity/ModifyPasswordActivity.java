package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;

public class ModifyPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText newPasswordAgain;
    private TextView error;
    public static final int UPDATE_UI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
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
        } else if (!oldPassword.getText().toString().equals("123")) {
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
                if (judge()) {
                    Toast.makeText(this,"密码修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
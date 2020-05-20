package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailboxActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editEmail;
    private TextView error;
    private String email;
    public static final int UPDATE_UI = 1;
    public static boolean student_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailbox);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        student_teacher = bundle.getBoolean("student_teacher");
        TextView mailboxReturn = findViewById(R.id.mailbox_return);
        TextView mailboxOk = findViewById(R.id.mailbox_ok);
        editEmail = findViewById(R.id.edit_email);
        error = findViewById(R.id.error_email);
        mailboxReturn.setOnClickListener(this);
        mailboxOk.setOnClickListener(this);
    }

    private boolean judge() {
        String strPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
        Pattern pattern = Pattern.compile(strPattern);
        Matcher matcher = pattern.matcher(editEmail.getText().toString());
        if (matcher.matches()) {
            if (!editEmail.getText().toString().equals(email)) {
                return true;
            } else {
                error.setText("新邮箱不能和老邮箱一样");
                reset();
                return false;
            }
        } else {
            error.setText("邮箱格式不正确");
            reset();
            return false;
        }
    }

    private void reset() {
        editEmail.setText("");
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
            case R.id.mailbox_return:
                finish();
                break;
            case R.id.mailbox_ok:
                if (student_teacher) {
                    email = LoginActivity.studentBean.getEmail();
                } else {
//                    email = LoginActivity.teacherBean.getEmail();
                }
                if (judge()) {
                    Toast.makeText(this,"邮箱修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
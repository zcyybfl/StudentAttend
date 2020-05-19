package com.example.studentattend.activity;

import androidx.annotation.NonNull;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.studentattend.R;
import com.example.studentattend.collector.ActivityCollector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BindMobileNumberActivity extends BaseActivity implements View.OnClickListener {

    private String tel;
    private EditText telephone;
    private TextView error;
    public static final int UPDATE_UI = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile_number);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        tel = bundle.getString("tel");
        TextView bindNumberReturn = findViewById(R.id.number_bind_return);
        telephone = findViewById(R.id.telephone);
        error = findViewById(R.id.error_phone);
        Button submitPhone = findViewById(R.id.submit_phone);
        submitPhone.setOnClickListener(this);
        bindNumberReturn.setOnClickListener(this);
    }

    //判断手机格式是否正确
    private boolean isMobileNO(String mobiles) {
        if (mobiles.equals("")) {
            error.setText("手机号不能为空");
            reset();
            return false;
        }
        String strPhone = "^((\\+|00)86)?((134\\d{4})|((13[0-3|5-9]|14[1|5-9]|15[0-9]|16[2|5-7]|17[0-8]|18[0-9]|19[0-2|5-9])\\d{8}))$";
        Pattern pattern = Pattern.compile(strPhone);
        Matcher matcher = pattern.matcher(mobiles);
        if (matcher.matches()) {
            if (!mobiles.equals(tel)) {
                return true;
            } else {
                error.setText("新手机号不能和原手机号相同");
                reset();
                return false;
            }
        } else {
            error.setText("手机号码格式不正确");
            reset();
            return false;
        }
    }

    private void reset() {
        telephone.setText("");
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
            case R.id.number_bind_return:
                finish();
                break;
            case R.id.submit_phone:
                if (isMobileNO(telephone.getText().toString())) {
                    ActivityCollector.finishAll();
                }
                break;
            default:
                break;
        }
    }
}
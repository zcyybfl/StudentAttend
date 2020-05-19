package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.os.Build;
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
    private EditText verificationCode;
    private TextView error;
    public static final int UPDATE_UI = 1;
    private long lastClickTime = 0;
    private int random;

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
        verificationCode = findViewById(R.id.verification_code);
        error = findViewById(R.id.error_phone);
        Button verificationCodeButton = findViewById(R.id.verification_code_button);
        Button submitPhone = findViewById(R.id.submit_phone);
        verificationCodeButton.setOnClickListener(this);
        submitPhone.setOnClickListener(this);
        bindNumberReturn.setOnClickListener(this);
    }

    //判断手机格式是否正确
    private boolean isMobileNO(String mobiles) {
        if (mobiles.equals("")) {
            error.setText("手机号不能为空");
            reset(true);
            return false;
        }
        String strPhone = "^((\\+|00)86)?((134\\d{4})|((13[0-3|5-9]|14[1|5-9]|15[0-9]|16[2|5|6|7]|17[0-8]|18[0-9]|19[0-2|5-9])\\d{8}))$";
        Pattern pattern = Pattern.compile(strPhone);
        Matcher matcher = pattern.matcher(mobiles);
        if (matcher.matches()) {
            if (!mobiles.equals(tel)) {
                return true;
            } else {
                error.setText("新手机号不能和原手机号相同");
                reset(true);
                return false;
            }
        } else {
            error.setText("手机号码格式不正确");
            reset(true);
            return false;
        }
    }

    private void reset(boolean flag) {
        if (flag) {
            telephone.setText("");
        }
        verificationCode.setText("");
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        long TIME = 15000;
        switch (v.getId()) {
            case R.id.number_bind_return:
                finish();
                break;
            case R.id.verification_code_button:
                long now = System.currentTimeMillis();
                if (now - lastClickTime > TIME) {
                    lastClickTime = now;
                    if (isMobileNO(telephone.getText().toString())) {
                        random = (int) ((Math.random() * 9 + 1) * 100000);
                        notice(random);
                    }
                } else {
                    error.setText("不可重复点击，请" + ((TIME - (now - lastClickTime)) / 1000)+ "s后再点击");
                    reset(true);
                }
                break;
            case R.id.submit_phone:
                if (judge()) {
                    ActivityCollector.finishAll();
                }
                break;
            default:
                break;
        }
    }

    private boolean judge() {
        if (verificationCode.getText().toString().equals("")) {
            error.setText("验证码不可为空");
            reset(false);
            return false;
        }
        if (verificationCode.getText().toString().equals(String.valueOf(random))) {
            return true;
        } else {
            error.setText("验证码不正确");
            reset(false);
            return false;
        }
    }

    private void notice(int random) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //高版本需要渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道，这里的第一个参数要和下面的channelId一样
            NotificationChannel notificationChannel = new NotificationChannel("1", "name", NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NONE就需要在系统的设置里面开启渠道，通知才能正常弹出
            manager.createNotificationChannel(notificationChannel);
        }
        Notification notification = new NotificationCompat.Builder(this,"1")
                .setContentTitle("验证码")
                .setContentText(String.valueOf(random))
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.app_icon))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //最重要的位置
                .setPriority(NotificationCompat.PRIORITY_MAX)
                //自动取消通知
                .setAutoCancel(true)
                .build();
        manager.notify(1,notification);
    }
}
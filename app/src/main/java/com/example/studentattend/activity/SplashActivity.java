package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studentattend.R;
import com.example.studentattend.saveread.UserManage;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.UserBean;
import com.example.studentattend.service.ServiceLogin;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {

    private int time = 5;//跳过倒计时提示5秒
    private TextView textView;
    Timer timer = new Timer();
    private Handler handler;
    private Runnable runnable;
    private boolean flag;
    public static BaseBean baseBean;
    Gson gson = new Gson();
    Bundle bundle=new Bundle();
    public static UserBean userBean;
    private ImageView splashImage;
    //判断服务端有无此账号
    private boolean judge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义全屏参数
        int full = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        getWindow().setFlags(full,full);
        setContentView(R.layout.activity_splash);
        initView();
        initImage();
        flag = UserManage.getInstance().hasUserInfo(this);
        if (flag) {
            ServiceLogin serviceLogin = new ServiceLogin();
            serviceLogin.init(UserManage.userInfo.getUserName(),UserManage.userInfo.getPassword(),UserManage.userInfo.getType());
            serviceLogin.start();
            baseBean = serviceLogin.show();
            if (baseBean.getMsg().equals("登录失败")) {
                judge = false;
                UserManage.getInstance().clear(SplashActivity.this);
            } else {
                judge = true;
            }
            String json = gson.toJson(baseBean.getData());
            userBean = gson.fromJson(json, UserBean.class);
        }
        timer.schedule(task,1000,1000);//等待时间一秒，停顿时间一秒
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                //从闪屏界面跳转到首界面
                if (flag && judge) {
                    Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                    if (UserManage.userInfo.getType().equals("student")) {
                        bundle.putBoolean("student_teacher", true);
                    } else {
                        bundle.putBoolean("student_teacher", false);
                    }
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        },5000);//延迟5S后发送handler信息
        //自动登录判断，SharedPreferences中有数据，则跳转到主页，没数据则跳转到登录页
    }

    private void initImage() {
        Glide
                .with(this)
                .load(Uri.parse("http://hsjnb.com/AttendStudent/splash.png"))
                .into(splashImage);
    }

    private void initView() {
        textView = findViewById(R.id.textView);//跳过
        splashImage = findViewById(R.id.splash_image);
        textView.setOnClickListener(this);//跳过监听
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    time--;
                    textView.setText("跳过" + time);
                    if (time < 0) {
                        timer.cancel();
                        textView.setVisibility(View.GONE);//倒计时到0隐藏字体
                    }
                }
            });
        }
    };

    /**
     * 点击跳过
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.textView) {//从闪屏界面跳转到首界面
            if (flag && judge) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                if (UserManage.userInfo.getType().equals("student")) {
                    bundle.putBoolean("student_teacher", true);
                } else {
                    bundle.putBoolean("student_teacher", false);
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }else {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
            }
            finish();
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }
        }
    }
}
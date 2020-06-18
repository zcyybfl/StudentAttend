package com.example.studentattend.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.example.studentattend.R;
import com.example.studentattend.saveread.UserManage;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.UserBean;
import com.example.studentattend.md5.Md5Utils;
import com.example.studentattend.service.ServiceLogin;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    public static BaseBean baseBean;
    Gson gson = new Gson();
    Bundle bundle=new Bundle();
    public static final int REGISTER = 1;
    public static final int FORGET = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button student_login = findViewById(R.id.student_login);
        Button teacher_login = findViewById(R.id.teacher_login);
        TextView register = findViewById(R.id.register);
        TextView forget = findViewById(R.id.forget);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        student_login.setOnClickListener(this);
        teacher_login.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_admin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.administration) {
            Intent admin_login = new Intent(LoginActivity.this, AdminLoginActivity.class);
            startActivity(admin_login);
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        ServiceLogin serviceLogin = new ServiceLogin();
        String username_Text = username.getText().toString();
        String password_Text = Md5Utils.md5(password.getText().toString());
        switch (v.getId()){
            case R.id.student_login:
                if (validateInput(username_Text,password_Text)){
                    serviceLogin.init(username_Text,password_Text,"student");
                    serviceLogin.start();
                    baseBean = serviceLogin.show();
                    String json = gson.toJson(baseBean.getData());
                    SplashActivity.userBean = gson.fromJson(json, UserBean.class);
                    if (judge(baseBean)){
                        UserManage.getInstance().saveUserInfo(this,username_Text,password_Text,"student");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //用Bundle携带数据，后面创建键值对时，记得把老师还是学生传进去，学生=true，老师=false
                        bundle.putBoolean("student_teacher", true);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }

                break;
            case R.id.teacher_login:
                if (validateInput(username_Text,password_Text)){
                    serviceLogin.init(username_Text,password_Text,"teacher");
                    serviceLogin.start();
                    baseBean = serviceLogin.show();
                    String json = gson.toJson(baseBean.getData());
                    SplashActivity.userBean = gson.fromJson(json, UserBean.class);
                    if (judge(baseBean)){
                        UserManage.getInstance().saveUserInfo(this,username_Text,password_Text,"teacher");
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //用Bundle携带数据，后面创建键值对时，记得把老师还是学生传进去，学生=true，老师=false
                        bundle.putBoolean("student_teacher", false);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(LoginActivity.this,"账户或密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.register:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = REGISTER;
                        handler.sendMessage(message);
                    }
                }).start();
                break;
            case R.id.forget:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = FORGET;
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
            if (msg.what == REGISTER) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("注册")
                        .setMessage("请选择注册类型")
                        .setNegativeButton("学生", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent registerStudent = new Intent(LoginActivity.this,RegisterActivity.class);
                                bundle.putBoolean("student_teacher",true);
                                registerStudent.putExtras(bundle);
                                startActivity(registerStudent);
                            }
                        })
                        .setPositiveButton("老师", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent registerTeacher = new Intent(LoginActivity.this,RegisterActivity.class);
                                bundle.putBoolean("student_teacher",false);
                                registerTeacher.putExtras(bundle);
                                startActivity(registerTeacher);
                            }
                        })
                        .setNeutralButton("取消",null)
                        .show();
            } else if (msg.what == FORGET) {
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("忘记密码")
                        .setMessage("请选择忘记类型")
                        .setNegativeButton("学生", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent forgetStudent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                                bundle.putBoolean("student_teacher",true);
                                forgetStudent.putExtras(bundle);
                                startActivity(forgetStudent);
                            }
                        })
                        .setPositiveButton("老师", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent forgetTeacher = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                                bundle.putBoolean("student_teacher",false);
                                forgetTeacher.putExtras(bundle);
                                startActivity(forgetTeacher);
                            }
                        })
                        .setNeutralButton("取消",null)
                        .show();
            }
        }
    };


    /**
     * 判断登录是否成功
     */
    public boolean judge(BaseBean baseBean1){
        return !baseBean1.getMsg().equals("登录失败");
    }

    /**
     * 验证账户密码是否输入为空
     */
    public boolean validateInput(String username,String password){
        if (username.isEmpty()){
            Toast.makeText(LoginActivity.this,"账户不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.isEmpty()){
            Toast.makeText(LoginActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}
package com.example.studentattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentattend.R;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.StudentBean;
import com.example.studentattend.md5.Md5Utils;
import com.example.studentattend.service.ServiceLogin;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText username;
    private EditText password;
    public static BaseBean baseBean;
    Gson gson = new Gson();
    public static StudentBean studentBean;
    Bundle bundle=new Bundle();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button student_login = findViewById(R.id.student_login);
        Button teacher_login = findViewById(R.id.teacher_login);
        TextView register = findViewById(R.id.register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        student_login.setOnClickListener(this);
        teacher_login.setOnClickListener(this);
        register.setOnClickListener(this);
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
        String password_Text = Md5Utils.md5(password.getText().toString(),"StudentAttend");
        switch (v.getId()){
            case R.id.student_login:
                if (validateInput(username_Text,password_Text)){
                    serviceLogin.init(username_Text,password_Text,"student");
                    serviceLogin.start();
                    baseBean = serviceLogin.show();
                    String json = gson.toJson(baseBean.getDate());
                    studentBean = gson.fromJson(json,StudentBean.class);
                    if (judge(baseBean)){
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
                    if (judge(baseBean)){
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
                Intent register_activity = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register_activity);
                break;
        }
    }


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
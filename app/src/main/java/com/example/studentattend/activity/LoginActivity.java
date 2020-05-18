package com.example.studentattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.studentattend.service.ServiceLogin;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button student_login;
    private Button teacher_login;
    private EditText username;
    private EditText password;
    private TextView register;
    public static BaseBean baseBean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        student_login = findViewById(R.id.student_login);
        teacher_login = findViewById(R.id.teacher_login);
        register = findViewById(R.id.register);
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
        switch (item.getItemId()){
            case R.id.administration:
                Intent admin_login = new Intent(LoginActivity.this,AdminLoginActivity.class);
                startActivity(admin_login);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        ServiceLogin serviceLogin = new ServiceLogin();
        String username_Text = username.getText().toString();
        String password_Text = password.getText().toString();
        switch (v.getId()){
            case R.id.student_login:
                if (validateInput(username_Text,password_Text)){
                    serviceLogin.init(username_Text,password_Text,"student");
                    serviceLogin.start();
                    baseBean = serviceLogin.show();
//                    Log.d("LoginActivity", "msg is " + baseBean.getMsg());
//                    Log.d("LoginActivity", "date is " + baseBean.getDate());
//                    Log.d("LoginActivity", "username is " + username_Text);
//                    Log.d("LoginActivity", "password is " + password_Text);
                    if (judge(baseBean)){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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
     * @param baseBean1
     * @return
     */
    public boolean judge(BaseBean baseBean1){
        if (!baseBean1.getMsg().equals("登录失败")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验证账户密码是否输入为空
     * @param username
     * @param password
     * @return
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
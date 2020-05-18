package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentattend.R;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.service.ServiceRegister;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText register_username;
    private EditText register_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button student_register = findViewById(R.id.student_register);
        Button teacher_register = findViewById(R.id.teacher_register);
        register_username  =findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        student_register.setOnClickListener(this);
        teacher_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        ServiceRegister serviceRegister = new ServiceRegister();
        String username_Text = register_username.getText().toString();
        String password_Text = register_password.getText().toString();
        BaseBean baseBean;
        switch (v.getId()){
            case R.id.student_register:
                if (validateInput(username_Text,password_Text)){
                    serviceRegister.init(username_Text,password_Text,"student");
                    serviceRegister.start();
                    baseBean = serviceRegister.show();
                    if (judge(baseBean)){
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
            case R.id.teacher_register:
                if (validateInput(username_Text,password_Text)){
                    serviceRegister.init(username_Text,password_Text,"teacher");
                    serviceRegister.start();
                    baseBean = serviceRegister.show();
                    if (judge(baseBean)){
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }

    public boolean judge(BaseBean baseBean1){
        return baseBean1.getMsg().equals("注册成功");
    }

    public boolean validateInput(String username,String password){
        if (username.isEmpty()){
            Toast.makeText(RegisterActivity.this,"账户不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}

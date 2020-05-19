package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.service.ServiceRegister;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText register_username;
    private EditText register_password;
    private Button student_register;
    private Button teacher_register;
    private TextView register_return;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        student_register = findViewById(R.id.student_register);
        teacher_register = findViewById(R.id.teacher_register);
        register_username  =findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        register_return = findViewById(R.id.register_return);
        student_register.setOnClickListener(this);
        teacher_register.setOnClickListener(this);
        register_return.setOnClickListener(this);
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
                    }else {
                        Toast.makeText(RegisterActivity.this,"用户名重复，请重新输入",Toast.LENGTH_SHORT).show();
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
                    }else {
                        Toast.makeText(RegisterActivity.this,"用户名重复，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.register_return:
                finish();
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

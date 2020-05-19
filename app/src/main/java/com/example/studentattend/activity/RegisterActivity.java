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
    private EditText register_name;
    private EditText register_sex;
    private EditText register_classmate;
    private EditText register_department;
    private EditText register_phone;
    private EditText register_email;
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
        register_sex = findViewById(R.id.register_sex);
        register_classmate = findViewById(R.id.register_class);
        register_name = findViewById(R.id.register_name);
        register_department = findViewById(R.id.register_department);
        register_phone = findViewById(R.id.register_phone);
        register_email = findViewById(R.id.register_email);
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
        String name_Text = register_name.getText().toString();
        String sex_Text = register_sex.getText().toString();
        String classmate_Text = register_classmate.getText().toString();
        String department_Text = register_department.getText().toString();
        String phone_Text = register_phone.getText().toString();
        String email_Text = register_email.getText().toString();
        BaseBean baseBean;
        switch (v.getId()){
            case R.id.student_register:
                if (validateInput(username_Text,password_Text,name_Text,sex_Text,classmate_Text,department_Text,phone_Text,email_Text)){
                    serviceRegister.init(username_Text,password_Text,name_Text,sex_Text,classmate_Text,phone_Text,department_Text,email_Text,"student");
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
                if (validateInput(username_Text,password_Text,name_Text,sex_Text,classmate_Text,department_Text,phone_Text,email_Text)){
                    //serviceRegister.init(username_Text,password_Text,"teacher");
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

    public boolean validateInput(String username,String password,String name,String sex,String classmate,String department,String phone,String email){
        if (username.isEmpty()){
            Toast.makeText(RegisterActivity.this,"学号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (name.isEmpty()){
            Toast.makeText(RegisterActivity.this,"名字不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (sex.isEmpty()){
            Toast.makeText(RegisterActivity.this,"性别不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (classmate.isEmpty()){
            Toast.makeText(RegisterActivity.this,"班级不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (department.isEmpty()){
            Toast.makeText(RegisterActivity.this,"系别不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (phone.isEmpty()){
            Toast.makeText(RegisterActivity.this,"电话不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else if (email.isEmpty()){
            Toast.makeText(RegisterActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
}

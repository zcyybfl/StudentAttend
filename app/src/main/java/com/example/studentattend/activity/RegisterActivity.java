package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.md5.Md5Utils;
import com.example.studentattend.service.ServiceRegister;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText register_username;
    private EditText register_password;
    private EditText register_name;
    private EditText register_sex;
    private EditText register_classmate;
    private EditText register_department;
    private EditText register_phone;
    private EditText register_email;
    //获取是老师还是学生登录
    public static boolean student_teacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button student_register = findViewById(R.id.student_register);
        Button teacher_register = findViewById(R.id.teacher_register);
        register_username  =findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        register_sex = findViewById(R.id.register_sex);
        register_classmate = findViewById(R.id.register_class);
        register_name = findViewById(R.id.register_name);
        register_department = findViewById(R.id.register_department);
        register_phone = findViewById(R.id.register_phone);
        register_email = findViewById(R.id.register_email);
        TextView register_return = findViewById(R.id.register_return);
        LinearLayout classmate = findViewById(R.id.classmate);
        student_register.setOnClickListener(this);
        teacher_register.setOnClickListener(this);
        register_return.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        student_teacher = bundle.getBoolean("student_teacher");
        if (student_teacher){
            student_register.setVisibility(View.VISIBLE);
            teacher_register.setVisibility(View.GONE);

        }else {
            classmate.setVisibility(View.GONE);
            student_register.setVisibility(View.GONE);
            teacher_register.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        ServiceRegister serviceRegister = new ServiceRegister();
        String username_Text = register_username.getText().toString();
        String password_Text = Md5Utils.md5(register_password.getText().toString());
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
                        Toast.makeText(RegisterActivity.this,"用户名(学号)重复，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.teacher_register:
                if (validateInput(username_Text,password_Text,name_Text,sex_Text,classmate_Text,department_Text,phone_Text,email_Text)){
                    serviceRegister.init(username_Text,password_Text,name_Text,sex_Text,classmate_Text,phone_Text,department_Text,email_Text,"teacher");
                    serviceRegister.start();
                    baseBean = serviceRegister.show();
                    if (judge(baseBean)){
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this,"用户名(教工号)重复，请重新输入",Toast.LENGTH_SHORT).show();
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
        if (!student_teacher){
            classmate = "1";
        }
        if (username.isEmpty()){
            Toast.makeText(RegisterActivity.this,"学号不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.isEmpty()){
            Toast.makeText(RegisterActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (name.isEmpty()){
            Toast.makeText(RegisterActivity.this,"名字不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (sex.isEmpty()){
            Toast.makeText(RegisterActivity.this,"性别不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (classmate.isEmpty()){
            Toast.makeText(RegisterActivity.this,"班级不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (department.isEmpty()){
            Toast.makeText(RegisterActivity.this,"系别不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.isEmpty()){
            Toast.makeText(RegisterActivity.this,"电话不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (email.isEmpty()){
            Toast.makeText(RegisterActivity.this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return false;
        } else if (judge(true)) {
            Toast.makeText(RegisterActivity.this,"电话格式不正确",Toast.LENGTH_SHORT).show();
            return false;
        } else if (judge(false)) {
            Toast.makeText(RegisterActivity.this,"邮箱格式不正确",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!sex.equals("男") && !sex.equals("女")) {
            Toast.makeText(RegisterActivity.this,"性别输入不正确",Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private boolean judge(boolean flag) {
        Matcher matcher;
        if (flag) {
            String strPhone = "^((\\+|00)86)?((134\\d{4})|((13[0-3|5-9]|14[1|5-9]|15[0-9]|16[2|5-7]|17[0-8]|18[0-9]|19[0-2|5-9])\\d{8}))$";
            Pattern pattern = Pattern.compile(strPhone);
            matcher = pattern.matcher(register_phone.getText().toString());
        } else {
            String strPattern = "^([a-zA-Z0-9_\\-.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
                    "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(]?)$";
            Pattern pattern = Pattern.compile(strPattern);
            matcher = pattern.matcher(register_email.getText().toString());
        }
        return !matcher.matches();
    }
}

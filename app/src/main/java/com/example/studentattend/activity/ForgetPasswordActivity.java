package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.md5.Md5Utils;
import com.example.studentattend.service.ServiceModify;
import com.example.studentattend.service.ServicePassword;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    public static boolean student_teacher;
    private EditText tel;
    private EditText email;
    private EditText password;
    private EditText id;
    BaseBean baseBean = null;
    String flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        student_teacher = bundle.getBoolean("student_teacher");
        TextView forgetReturn = findViewById(R.id.forget_return);
        Button passwordForget = findViewById(R.id.password_forget);
        id = findViewById(R.id.forget_username);
        tel = findViewById(R.id.forget_phone);
        email = findViewById(R.id.forget_email);
        password = findViewById(R.id.forget_password);
        forgetReturn.setOnClickListener(this);
        passwordForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_return:
                finish();
                break;
            case R.id.password_forget:
                if (student_teacher) {
                    flag = "student";
                } else {
                    flag = "teacher";
                }
                if (judge()) {
                    ServiceModify serviceModify = new ServiceModify();
                    serviceModify.init(id.getText().toString(),"password", Md5Utils.md5(password.getText().toString()), flag);
                    serviceModify.start();
                    baseBean = serviceModify.show();
                    if (judge_modify(baseBean)){
                        Toast.makeText(this,"密码修改成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"密码修改失败",Toast.LENGTH_SHORT).show();
                    }
                    finish();
                } else {
                    Toast.makeText(this,"账号或电话或邮箱不正确",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public boolean judge_modify(BaseBean baseBean1){
        return baseBean1.getMsg().equals("修改成功");
    }

    private boolean judge() {
        if (id.getText().toString().isEmpty()
                || tel.getText().toString().isEmpty()
                || email.getText().toString().isEmpty()
                || password.getText().toString().isEmpty()) {
            Toast.makeText(this,"账号，电话，邮箱，新密码不能为空",Toast.LENGTH_SHORT).show();
        }
        ServicePassword servicePassword = new ServicePassword();
        servicePassword.init(id.getText().toString(),tel.getText().toString(),email.getText().toString(),flag);
        servicePassword.start();
        baseBean = servicePassword.show();
        return baseBean.getMsg().equals("验证成功");
    }
}
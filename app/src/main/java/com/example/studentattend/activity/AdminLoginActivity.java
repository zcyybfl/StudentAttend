package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText admin_username;
    private EditText admin_password;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Button admin_login = findViewById(R.id.admin_login);
        TextView admin_return = findViewById(R.id.admin_return);
        admin_username = findViewById(R.id.admin_username);
        admin_password = findViewById(R.id.admin_password);

        admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = admin_username.getText().toString();
                password = admin_password.getText().toString();
                Log.d("AdminLoginActivity", "password" + password);
                if (judge(username,password)){
                    Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(AdminLoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        admin_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean judge(String username,String password){
        if (username.equals("admin")){
            return password.equals("123456");
        }
        return false;
    }
}

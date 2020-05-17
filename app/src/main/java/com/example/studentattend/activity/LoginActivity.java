package com.example.studentattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentattend.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{



    private Button student_login;
    private TextView register;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button student_login = findViewById(R.id.student_login);

        student_login = findViewById(R.id.student_login);
        register = findViewById(R.id.register);

        student_login.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.student_login:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.register:
                Intent register_activity = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(register_activity);
                break;
        }
    }
}
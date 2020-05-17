package com.example.studentattend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentattend.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button student_login = findViewById(R.id.student_login);
        student_login.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.button_admin,menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.student_login:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
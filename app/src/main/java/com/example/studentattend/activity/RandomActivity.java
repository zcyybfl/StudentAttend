package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;

import java.util.Random;

public class RandomActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleLayoutTitleText;
    private TextView randomNumberResult;
    private Button titleLayoutBackButton;
    private EditText randomNumberMinInput;
    private EditText randomNumberMaxInput;
    private TextView submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        findViewById();
        initView();
    }

    private void initView() {
        titleLayoutTitleText.setText("随机数");
        titleLayoutBackButton.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    private void findViewById() {
        titleLayoutTitleText = findViewById(R.id.title_layout_title_text);
        titleLayoutBackButton = findViewById(R.id.title_layout_back_button);
        randomNumberResult = findViewById(R.id.random_number_result);
        randomNumberMinInput = findViewById(R.id.random_number_min_input);
        randomNumberMaxInput = findViewById(R.id.random_number_max_input);
        submit = findViewById(R.id.submit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_layout_back_button:
                finish();
                break;
            case R.id.submit:
                createRandomNumber();
                break;
            default:
                break;
        }
    }

    private void createRandomNumber() {
        int min = Integer.parseInt(randomNumberMinInput.getText().toString().trim());
        int max = Integer.parseInt(randomNumberMaxInput.getText().toString().trim());
        if (min > max) {
            Toast.makeText(this,"最小值似乎比最大值还要大",Toast.LENGTH_SHORT).show();
            return;
        }
        if (min == max) {
            randomNumberResult.setText(String.valueOf(min));
            return;
        }
        Random random = new Random();
        if (max - min == 1) {
            if (random.nextBoolean()) {
                randomNumberResult.setText(String.valueOf(max));
            } else {
                randomNumberResult.setText(String.valueOf(min));
            }
            return;
        }
        int result;
        if (max <= 0) {
            result = -random.nextInt(max - min + 1) + max;
        } else if (min < 0) {
            if (random.nextBoolean()) {
                result = -random.nextInt(-min + 1);
            } else {
                result = random.nextInt(max + 1);
            }
        } else {
            result = random.nextInt(max - min + 1) + min;
        }
        randomNumberResult.setText(String.valueOf(result));
    }
}
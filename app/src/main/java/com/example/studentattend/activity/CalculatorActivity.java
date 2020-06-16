package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;

public class CalculatorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleText;
    private Button returnButton;
    private TextView resultDisplay;
    private Button clear;
    private Button delete;
    private Button division;
    private Button seven;
    private Button eight;
    private Button nine;
    private Button multiplication;
    private Button four;
    private Button five;
    private Button six;
    private Button subtraction;
    private Button percent;
    private Button one;
    private Button two;
    private Button three;
    private Button add;
    private Button zero;
    private Button point;
    private Button equal;
    // 存储数字及结果
    private double currentResult = 0.0;
    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 当前运算的运算符
    private String operator = "=";
    // 操作是否合法
    private boolean operateValidFlag = true;
    //记录上次输入是否是符号
    private boolean symbol = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        findViewById();
        initTitle();
    }

    private void initTitle() {
        titleText.setText("计算器");
    }

    private void findViewById() {
        titleText = findViewById(R.id.title_layout_title_text);
        returnButton = findViewById(R.id.title_layout_back_button);
        resultDisplay = findViewById(R.id.result_display);
        clear =findViewById(R.id.clear);
        delete = findViewById(R.id.delete);
        division = findViewById(R.id.division);
        percent = findViewById(R.id.percent);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        multiplication = findViewById(R.id.multiplication);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        subtraction = findViewById(R.id.subtraction);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        add = findViewById(R.id.add);
        zero = findViewById(R.id.zero);
        point = findViewById(R.id.point);
        equal = findViewById(R.id.equal);
        setOnClickListener();
    }

    private void setOnClickListener() {
        returnButton.setOnClickListener(this);
        clear.setOnClickListener(this);
        delete.setOnClickListener(this);
        division.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        multiplication.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        subtraction.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        add.setOnClickListener(this);
        zero.setOnClickListener(this);
        point.setOnClickListener(this);
        equal.setOnClickListener(this);
        percent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_layout_back_button:
                back();
                finish();
                break;
            case R.id.clear:
                clear();
                break;
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
            case R.id.point:
                setNum(((Button) v).getText().toString());
                break;
            case R.id.add:
            case R.id.subtraction:
            case R.id.multiplication:
            case R.id.division:
            case R.id.percent:
            case R.id.equal:
                setOperator(((Button) v).getText().toString());
                break;
            case R.id.delete:
                setDel();
                break;
            default:
                break;
        }
    }

    private void setDel() {
        String str = resultDisplay.getText().toString();
        if (!str.equals("0")) {
            str = str.substring(0,str.length() - 1);
            resultDisplay.setText(str);
            if (symbol) {
                operator = "=";
            }
            symbol = false;
            firstDigit = false;
        }
        if (str.isEmpty()) {
            resultDisplay.setText("0");
        }
    }

    private void setOperator(String label) {
        if (!symbol) {
            switch (operator) {
                case "÷":
                    // 除法运算
                    // 如果当前结果文本框中的值等于0
                    if (getNumFromResultDisplay() == 0.0) {
                        // 操作不合法
                        operateValidFlag = false;
                        resultDisplay.setText("除数不能为零！");
                    } else {
                        currentResult /= getNumFromResultDisplay();
                    }
                    break;
                case "+":
                    // 加法运算
                    currentResult += getNumFromResultDisplay();
                    break;
                case "-":
                    // 减法运算
                    currentResult -= getNumFromResultDisplay();
                    break;
                case "×":
                    // 乘法运算
                    currentResult *= getNumFromResultDisplay();
                    break;
                case "%":
                    currentResult %= getNumFromResultDisplay();
                    break;
                case "=":
                    // 赋值运算
                    currentResult = getNumFromResultDisplay();
                    break;
                default:
                    break;
            }
            if (operateValidFlag) {
                // 双精度浮点数的运算
                long t1;
                double t2;
                t1 = (long) currentResult;
                t2 = currentResult - t1;
                if (t2 == 0) {
                    resultDisplay.setText(String.valueOf(t1));
                } else {
                    resultDisplay.setText(String.valueOf(currentResult));
                }
            }
            firstDigit = true;
            operateValidFlag = true;
            symbol = true;
        }
        // 运算符等于用户按的按钮
        operator = label;
    }

    private double getNumFromResultDisplay() {
        double result = 0;
        try {
            result = Double.parseDouble(resultDisplay.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this,"您输入的不是数字",Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    private void setNum(String input) {
        boolean flag = true;
        if (firstDigit) {
            // 输入的第一个数字
            if (input.equals(".")) {
                resultDisplay.setText("0.");
            } else if(input.equals("0") && resultDisplay.getText().toString().equals("0")) {
                flag = false;
            } else {
                resultDisplay.setText(input);
            }
        } else if (input.equals("0") && resultDisplay.getText().toString().equals("0")) {
            flag = false;
        } else if (input.equals(".") && (!resultDisplay.getText().toString().contains("."))) {
            // 输入的是小数点，并且之前没有小数点，则将小数点附在结果文本框的后面
            String str = resultDisplay.getText().toString();
            str += ".";
            resultDisplay.setText(str);
        } else if (!input.equals(".")) {
            // 如果输入的不是小数点，则将数字附在结果文本框的后面
            String str = resultDisplay.getText().toString();
            str += input;
            resultDisplay.setText(str);
        }
        // 以后输入的肯定不是第一个数字了
        if (flag) {
            firstDigit = false;
        }
        symbol = false;
    }

    private void clear() {
        resultDisplay.setText("0");
        firstDigit = true;
        operator = "=";
    }

    private void back() {
        String currentResult = resultDisplay.getText().toString();
        int i = currentResult.length();
        if (i > 0) {
            currentResult = currentResult.substring(0,i - 1);// 将文本最后一个字符去掉
            if (currentResult.length() == 0) {// 如果没有了内容，初始化计算器的各种值
                resultDisplay.setText("0");
            } else {
                resultDisplay.setText(currentResult); // 显示新的文本
            }
        }
    }

    @Override
    protected void onDestroy() {
        back();
        super.onDestroy();
    }
}
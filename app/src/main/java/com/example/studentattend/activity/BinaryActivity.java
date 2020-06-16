package com.example.studentattend.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentattend.R;
import com.example.studentattend.util.ClipboardUtil;

/**
 * 使用Integer或Long的toBinaryString方法将整数转换为二进制。
 *
 * 使用Integer或Long的toOctalString方法将整数转换为八进制。
 *
 * 使用Integer或Long的toHexString方法将整数转换为十六进制。
 *
 * 使用Integer或Long的toString(int i)方法可以将其他进制的整数转换为十进制的整数的字符串表示。
 */
public class BinaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Button convertBtn;
    private Spinner source;
    private Spinner target;
    private TextView resultTxt;
    private EditText inputTxt;
    private Button backBtn;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binary);
        findViewById();
        initView();
    }

    private void findViewById() {
        title = findViewById(R.id.title_layout_title_text);
        backBtn = findViewById(R.id.title_layout_back_button);
        convertBtn = findViewById(R.id.radix_convert_btn);
        inputTxt = findViewById(R.id.radix_input_txt);
        resultTxt = findViewById(R.id.radix_result);
        source = findViewById(R.id.radix_source);
        target = findViewById(R.id.radix_target);
    }

    private void initView() {
        title.setText("进制转换");
        backBtn.setOnClickListener(this);
        convertBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_layout_back_button:
                finish();
                break;
            case R.id.radix_convert_btn:
                try {
                    convert();
                    //复制结果到剪贴板
                    ClipboardUtil.copyToClipboard(resultTxt.getText().toString().trim());
                    Toast.makeText(this,"结果已经复制到剪贴板",Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this,"数据异常，无法解析",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void convert() {
        if (!isLegal()) {
            Toast.makeText(this,"输入不合法",Toast.LENGTH_SHORT).show();
        }
        String s = inputTxt.getText().toString().trim();
        switch (source.getSelectedItemPosition()) {
            case 0://二进制
                switch (target.getSelectedItemPosition()) {
                    case 0: // 二进制
                        resultTxt.setText(s);
                        break;
                    case 1: // 八进制
                        resultTxt.setText(Integer.toOctalString(Integer.parseInt(s, 2)));
                        break;
                    case 2: // 十进制
                        resultTxt.setText(Integer.valueOf(s, 2).toString());
                        break;
                    case 3: // 十六进制
                        resultTxt.setText(Integer.toHexString(Integer.parseInt(s, 2)));
                        break;
                    default:
                        break;
                }
                break;
            case 1:// 8进制
                switch (target.getSelectedItemPosition()) {
                    case 0:
                        resultTxt.setText(Integer.toBinaryString(Integer.parseInt(s)));
                        break;
                    case 1:
                        resultTxt.setText(s);
                        break;
                    case 2:
                        resultTxt.setText(Integer.valueOf(s, 8).toString());
                        break;
                    case 3:
                        resultTxt.setText(Integer.toHexString(Integer.valueOf(s, 8)));
                        break;
                    default:
                        break;
                }
                break;
            case 2:// 10进制
                switch (target.getSelectedItemPosition()) {
                    case 0:
                        resultTxt.setText(Integer.toBinaryString(Integer.parseInt(s)));
                        break;
                    case 1:
                        resultTxt.setText(Integer.toOctalString(Integer.parseInt(s)));
                        break;
                    case 2:
                        resultTxt.setText(s);
                        break;
                    case 3:
                        resultTxt.setText(Integer.toHexString(Integer.parseInt(s)));
                        break;
                    default:
                        break;
                }
                break;
            case 3: // 16进制
                switch (target.getSelectedItemPosition()) {
                    case 0:
                        resultTxt.setText(Integer.toBinaryString(Integer.valueOf(s, 16)));
                        break;
                    case 1:
                        resultTxt.setText(Integer.toOctalString(Integer.valueOf(s, 16)));
                        break;
                    case 2:
                        resultTxt.setText(Integer.valueOf(s, 16).toString());
                        break;
                    case 3:
                        resultTxt.setText(s);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    private boolean isLegal() {
        String s = inputTxt.getText().toString().trim();
        if (s.length() == 0) {
            return false;
        }
        for (char c : s.toCharArray()) {
            switch (source.getSelectedItemPosition()) {
                case 0:
                    if (c != '0' && c != '1') {
                        return false;
                    }
                    break;
                case 1:
                    if ('0' > c || c > '7') {
                        return false;
                    }
                    break;
                case 2:
                    if ('0' > c || c > '9') {
                        return false;
                    }
                    break;
                case 3:
                    if (c <= 'F' && c >= 'A') {
                        c = (char) (c + 32);
                    }
                    if (!('0' <= c && c <= '9' || 'a' <= c && c <= 'f')) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
}
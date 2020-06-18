package com.example.studentattend.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.activity.MainActivity;
import com.example.studentattend.activity.SplashActivity;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.ClassCourseInquireBean;
import com.example.studentattend.service.ServiceAddTeacherAttendInfo;
import com.example.studentattend.service.ServiceStudentModifyAttendInfo;
import com.example.studentattend.service.ServiceTeacherSearchClass;

import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class DashboardFragment extends Fragment implements View.OnClickListener {
    public static final int attendButtonTea = 1;
    private EditText editText;
    private TextView text;
    private Context mContext;
    private Spinner spinner;
    private String attendClassNum;
    private int attendNumTea;
    private static long lastClickTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root;
        if (MainActivity.student_teacher){
         root = inflater.inflate(R.layout.fragment_attendstu, container, false);
            editText = root.findViewById(R.id.attendNumber);
            Button button = root.findViewById(R.id.attendButton);
            button.setOnClickListener(this);
        }
        else {
            root = inflater.inflate(R.layout.fragment_attendtea, container, false);
            spinner = root.findViewById(R.id.attendSpinner);
            Button button2 = root.findViewById(R.id.attendButtonTea);
            button2.setOnClickListener(this);
            text = root.findViewById(R.id.attendNumTea);
            getClassNum();
        }
        return root;
    }

    @SuppressLint("SimpleDateFormat")
    public static  String getLocalDatetimeString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    public static boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis(); // 此方法仅用于Android
        if (time - lastClickTime < 5000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attendButton:
                String attendNumberStu = editText.getText().toString().trim();//签到
                if (attendNumberStu.isEmpty()){
                    Toast.makeText(mContext,"签到码为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    signDataStu(attendNumberStu);
                }
                break;
            case R.id.attendButtonTea:
                if (!isFastDoubleClick()) {
                    attendNumTea = (int) (100000 + Math.random() * (999999 - 100000 + 1));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = attendButtonTea;
                            handler.sendMessage(message);
                        }
                    }).start();
                    signDataTea();
                }
                else
                    Toast.makeText(mContext,"签到过于频繁请稍后发起签到",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        @SuppressLint("SetTextI18n")
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == attendButtonTea){
                text.setText("签到码:\t"+attendNumTea);
            }
        }
    };

    //获取班级号放入classNum需要获取服务器端的class
    public void getClassNum(){
        List<ClassCourseInquireBean> list;
        ServiceTeacherSearchClass serviceTeacherSearchClass = new ServiceTeacherSearchClass();
        serviceTeacherSearchClass.init(SplashActivity.userBean.getSno());
        serviceTeacherSearchClass.start();
        list = serviceTeacherSearchClass.show();
        String[] classNum = new String[list.size()];
        for (int i = 0;i < list.size();i++){
            classNum[i] = list.get(i).getClassId();
        }
        spinnerAdapterInit(classNum);
    }

    public void spinnerAdapterInit(final String [] classNum){
        //创建ArrayAdapter对象
        ArrayAdapter<String> adapter= new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, classNum);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendClassNum=classNum[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    //老师签到向服务器传送的数据
    public void signDataTea(){
        ServiceAddTeacherAttendInfo serviceAddTeacherAttendInfo = new ServiceAddTeacherAttendInfo();
        String attendTime = getLocalDatetimeString();//时间
        String attendId = SplashActivity.userBean.getSno() + "-" + attendClassNum + "-" + attendTime;//签到id
        serviceAddTeacherAttendInfo.init(SplashActivity.userBean.getSno(), attendTime,attendClassNum, attendId,String.valueOf(attendNumTea));
        serviceAddTeacherAttendInfo.start();
        BaseBean baseBean = serviceAddTeacherAttendInfo.show();
        if (baseBean.getMsg().equals("成功")){
            Toast.makeText(mContext, "生成签到码成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "生成签到码失败", Toast.LENGTH_SHORT).show();
        }
    }

    //学生签到需要向服务器传送的数据
    public  void signDataStu(String attendNumberStu){//传入的是签到码
        ServiceStudentModifyAttendInfo serviceStudentModifyAttendInfo = new ServiceStudentModifyAttendInfo();
        serviceStudentModifyAttendInfo.init(SplashActivity.userBean.getSno(),SplashActivity.userBean.getClassmate(),attendNumberStu);
        serviceStudentModifyAttendInfo.start();
        BaseBean baseBean = serviceStudentModifyAttendInfo.show();
        switch (baseBean.getMsg()) {
            case "签到成功":
                Toast.makeText(mContext, "你已签到成功", Toast.LENGTH_SHORT).show();
                break;
            case "你已签到":
                Toast.makeText(mContext, "请勿重复签到", Toast.LENGTH_SHORT).show();
                break;
            case "已结束":
                Toast.makeText(mContext, "签到已结束", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(mContext, "签到码无效或你不属于此班级", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
package com.example.studentattend.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    public static final int attendButtonTea = 1;
    private String attendNumberStu;
    private String attendTime;
    private String attendTimeTea;
    private EditText editText;
    private TextView text;
    private Button button;
    private Button button2;
    private Context mContext;
    private  View root;
    private Spinner spinner;
    private String attendClassNum;
    private String attendId;
    private int attendNumTea;
    private static long lastClickTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (MainActivity.student_teacher){
         root = inflater.inflate(R.layout.fragment_attendstu, container, false);
            editText = root.findViewById(R.id.attendNumber);
            button = root.findViewById(R.id.attendButton);
            button.setOnClickListener(this);
        }
        else {
            root = inflater.inflate(R.layout.fragment_attendtea, container, false);
            spinner = root.findViewById(R.id.attendSpinner);
            button2 = root.findViewById(R.id.attendButtonTea);
            button2.setOnClickListener(this);
            text = root.findViewById(R.id.attendNumTea);
            getclass();
        }
        return root;
    }

    @SuppressLint("SimpleDateFormat")
    public static  String getLocalDatetimeString(String local) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return
                simpleDateFormat.format(date);

    }
    public static boolean isFastDoubleClick() {
        long time = SystemClock.uptimeMillis(); // 此方法仅用于Android
        if (time - lastClickTime < 4000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attendButton:
                attendNumberStu=editText.getText().toString().trim();//签到
                if (attendNumberStu.equals("")){
                    Toast.makeText(mContext,"签到码为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    signdatastu(attendNumberStu);
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
                    signdatatea();
                }
                else
                    Toast.makeText(mContext,"签到过于频繁请稍后发起签到",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==attendButtonTea){
                text.setText("签到码为"+attendNumTea);
            }
        }
    };

    //获取班级号放入classnum需要获取服务器端的class
    public void getclass(){
        List<ClassCourseInquireBean> list = new ArrayList<>();
        ServiceTeacherSearchClass serviceTeacherSearchClass = new ServiceTeacherSearchClass();
        serviceTeacherSearchClass.init(SplashActivity.userBean.getSno());
        serviceTeacherSearchClass.start();
        list = serviceTeacherSearchClass.show();
        String[] classnum = new String[list.size()];
        for (int i = 0;i<list.size();i++){
            classnum[i] = list.get(i).getClassId();
        }
        //String[] classnum = list.toArray(new String[list.size()]);
         //String[] classnum = new String[]{"123","234","567","789"};
        Spinneradapterinit(classnum);
    }
    public void Spinneradapterinit(final String [] classnum){
        //创建ArrayAdapter对象
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(mContext,android.R.layout.simple_list_item_1,classnum);
        spinner.setAdapter(adapter);
        /**选项选择监听*/
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendClassNum=classnum[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    //老师签到向服务器传送的数据
    public void signdatatea(){
        ServiceAddTeacherAttendInfo serviceAddTeacherAttendInfo = new ServiceAddTeacherAttendInfo();
        SplashActivity.userBean.getSno();//教工号
        attendTime= getLocalDatetimeString("GMT+8");//时间
        attendId = SplashActivity.userBean.getSno()+attendClassNum+attendTimeTea;//签到id
        serviceAddTeacherAttendInfo.init(SplashActivity.userBean.getSno(),attendTimeTea,attendClassNum,attendId,String.valueOf(attendNumTea));
        serviceAddTeacherAttendInfo.start();
        BaseBean baseBean = serviceAddTeacherAttendInfo.show();
        if (baseBean.getMsg().equals("成功")){
            Toast.makeText(mContext, "生成签到码成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "生成签到码失败", Toast.LENGTH_SHORT).show();
        }
    }
    //学生签到需要向服务器传送的数据
    public  void signdatastu(String AttendNumberstu){//传入的是签到码
        ServiceStudentModifyAttendInfo serviceStudentModifyAttendInfo = new ServiceStudentModifyAttendInfo();
        serviceStudentModifyAttendInfo.init(SplashActivity.userBean.getSno(),SplashActivity.userBean.getClassmate(),AttendNumberstu);
        serviceStudentModifyAttendInfo.start();
        BaseBean baseBean = serviceStudentModifyAttendInfo.show();
        if (baseBean.getMsg().equals("签到成功")){
            Toast.makeText(mContext, "你已签到成功", Toast.LENGTH_SHORT).show();
        }else if (baseBean.getMsg().equals("你已签到")){
            Toast.makeText(mContext, "请勿重复签到", Toast.LENGTH_SHORT).show();
        }else if (baseBean.getMsg().equals("已结束")){
            Toast.makeText(mContext, "签到已结束", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "签到码无效或你不属于此班级", Toast.LENGTH_SHORT).show();
        }
        SplashActivity.userBean.getSno();//学号
        SplashActivity.userBean.getClassmate();//班级
        attendTime= getLocalDatetimeString("GMT+8");//时间
        Log.d("2",attendTime);
    }
}
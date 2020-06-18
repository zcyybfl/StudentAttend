package com.example.studentattend.fragment;

import android.content.Context;
import android.net.wifi.aware.DiscoverySession;
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
import androidx.lifecycle.ViewModelProvider;

import com.example.studentattend.R;
import com.example.studentattend.activity.MainActivity;
import com.example.studentattend.activity.SplashActivity;
import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.ClassCourseInquireBean;
import com.example.studentattend.model.DashboardViewModel;
import com.example.studentattend.service.ServiceAddTeacherAttendInfo;
import com.example.studentattend.service.ServiceStudentModifyAttendInfo;
import com.example.studentattend.service.ServiceTeacherSearchClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    public static final int attendButtontea = 1;
    private String AttendNumberstu;
    private String AttendNumbertea;
    private String AttendTime;
    private String AttendTimetea;
    private EditText editText;
    private TextView text;
    private Button button;
    private Button button2;
    private Context mContext;
    private  View root;
    private Spinner spinner;
    private String attendclassnum;
    private String attendid;
    private int attendnumtea;
    private static long lastClickTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        if (MainActivity.student_teacher){
         root = inflater.inflate(R.layout.fragment_attendstu, container, false);
            editText = root.findViewById(R.id.attendnumber);
            button = root.findViewById(R.id.attendButton);
            button.setOnClickListener(this);
        }
        else if(!MainActivity.student_teacher) {
            root = inflater.inflate(R.layout.fragment_attendtea, container, false);
            spinner = root.findViewById(R.id.attendspinner);
            button2 = root.findViewById(R.id.attendButtontea);
            button2.setOnClickListener(this);
            text = root.findViewById(R.id.attendnumtea);
            getclass();
        }
        return root;
    }

    public static  String getLocalDatetimeString(String local) {

        Calendar cal = new
                GregorianCalendar(TimeZone.getTimeZone(local));

        cal.setTimeInMillis(Calendar.getInstance().getTimeInMillis());

        String date = cal.get(Calendar.YEAR) + "-"
                + (cal.get(Calendar.MONTH) + 1) + "-"
                + cal.get(Calendar.DAY_OF_MONTH);

        String time = cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":"
                + cal.get(Calendar.SECOND);

        return
                date + " "  + time;

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
                AttendNumberstu=editText.getText().toString().trim();//签到
                Log.d("1", AttendNumberstu);
                if (AttendNumberstu.equals("")){
                    Toast.makeText(mContext,"签到码为空",Toast.LENGTH_SHORT).show();
                }
                else {
                    signdatastu(AttendNumberstu);
                }
                break;
            case R.id.attendButtontea:
                if (!isFastDoubleClick()) {
                    attendnumtea = (int) (100000 + Math.random() * (999999 - 100000 + 1));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            message.what = attendButtontea;
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==attendButtontea){

                text.setText("签到码为"+attendnumtea);
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
                attendclassnum=classnum[position];
                Toast.makeText(mContext, "选择了班级号" + classnum[position], Toast.LENGTH_SHORT).show();
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
        AttendTimetea= getLocalDatetimeString("GMT+8");//时间
        attendid = SplashActivity.userBean.getSno()+attendclassnum+AttendTimetea;//签到id
        serviceAddTeacherAttendInfo.init(SplashActivity.userBean.getSno(),AttendTimetea,attendclassnum,attendid,String.valueOf(attendnumtea));
        serviceAddTeacherAttendInfo.start();
        BaseBean baseBean = serviceAddTeacherAttendInfo.show();
        if (baseBean.getMsg().equals("成功")){
            Toast.makeText(mContext, "生成签到码成功", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "生成签到码失败", Toast.LENGTH_SHORT).show();
        }
        Log.d("1",attendclassnum);//班级
        Log.d("2",AttendTimetea);
        Log.d("3",""+attendnumtea);
        Log.d("4",attendid);
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
        AttendTime= getLocalDatetimeString("GMT+8");//时间
        Log.d("2",AttendTime);
    }
}
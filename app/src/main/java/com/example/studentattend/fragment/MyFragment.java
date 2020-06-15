package com.example.studentattend.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.saveread.UserManage;
import com.example.studentattend.activity.AttendRecordStudentActivity;
import com.example.studentattend.activity.AttendRecordTeacherActivity;
import com.example.studentattend.activity.LoginActivity;
import com.example.studentattend.activity.MailboxActivity;
import com.example.studentattend.activity.MainActivity;
import com.example.studentattend.activity.MobileNumber;
import com.example.studentattend.activity.ModifyPasswordActivity;
import com.example.studentattend.activity.SplashActivity;
import com.example.studentattend.adapter.MyAdapter;
import com.example.studentattend.collector.ActivityCollector;
import com.example.studentattend.dao.MyBean;
import com.example.studentattend.version.APKVersionCodeUtils;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<MyBean> myBeanList = new ArrayList<>();

    Bundle bundle=new Bundle();
    View view;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    private boolean judgeStuTea() {
        return MainActivity.student_teacher;
    }

    //学生
    private void initMyMenuStu() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyBean name = new MyBean("名字",SplashActivity.userBean.getName(),R.drawable.ic_null);
                myBeanList.add(name);
                MyBean stuId = new MyBean("学号",SplashActivity.userBean.getSno(),R.drawable.ic_null);
                myBeanList.add(stuId);
                MyBean classId = new MyBean("班级号",SplashActivity.userBean.getClassmate(),R.drawable.ic_null);
                myBeanList.add(classId);
                MyBean system = new MyBean("系",SplashActivity.userBean.getDepartment(),R.drawable.ic_null);
                myBeanList.add(system);
                MyBean gender = new MyBean("性别",SplashActivity.userBean.getSex(),R.drawable.ic_null);
                myBeanList.add(gender);
                MyBean telephone = new MyBean("手机号",SplashActivity.userBean.getPhone(),R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(telephone);
                MyBean record = new MyBean("签到记录","",R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(record);
                MyBean mailbox = new MyBean("邮箱",SplashActivity.userBean.getEmail(),R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(mailbox);
                MyBean modifyPassword = new MyBean("修改密码","",R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(modifyPassword);
                MyBean about = new MyBean("关于", APKVersionCodeUtils.getVerName(requireContext()),R.drawable.ic_null);
                myBeanList.add(about);
            }
        }).start();
    }

    //老师
    private void initMyMenuTea() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyBean name = new MyBean("名字", SplashActivity.userBean.getName(),R.drawable.ic_null);
                myBeanList.add(name);
                MyBean teaId = new MyBean("教工号",SplashActivity.userBean.getSno(),R.drawable.ic_null);
                myBeanList.add(teaId);
                MyBean system = new MyBean("系",SplashActivity.userBean.getDepartment(),R.drawable.ic_null);
                myBeanList.add(system);
                MyBean gender = new MyBean("性别",SplashActivity.userBean.getSex(),R.drawable.ic_null);
                myBeanList.add(gender);
                MyBean telephone = new MyBean("手机号",SplashActivity.userBean.getPhone(),R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(telephone);
                MyBean record = new MyBean("签到记录","",R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(record);
                MyBean mailbox = new MyBean("邮箱",SplashActivity.userBean.getEmail(),R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(mailbox);
                MyBean modifyPassword = new MyBean("修改密码","",R.drawable.ic_baseline_chevron_right_24);
                myBeanList.add(modifyPassword);
                MyBean about = new MyBean("关于", APKVersionCodeUtils.getVerName(requireContext()),R.drawable.ic_null);
                myBeanList.add(about);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_login:
                new AlertDialog.Builder(requireContext())
                        .setTitle("退出登录")
                        .setMessage("您确定退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserManage.getInstance().clear(requireContext());
                                Intent quitLogin = new Intent(getContext(), LoginActivity.class);
                                startActivity(quitLogin);
                                //结束当前activity
                                ActivityCollector.finishAll();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
                break;
            case R.id.quit:
                new AlertDialog.Builder(requireContext())
                        .setTitle("退出")
                        .setMessage("您确定退出")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCollector.finishAll();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!judgeStuTea()) {
            //如果当为老师时，listView的position++;
            position++;
        }
        switch (position) {
            case 5:
                Intent mobileNumber = new Intent(getContext(), MobileNumber.class);
                bundle.putBoolean("student_teacher",judgeStuTea());
                mobileNumber.putExtras(bundle);
                startActivity(mobileNumber);
                break;
            case 6:
                if (judgeStuTea()) {
                    Intent recordStudent = new Intent(getContext(), AttendRecordStudentActivity.class);
                    startActivity(recordStudent);
                } else {
                    Intent recordTeacher = new Intent(getContext(), AttendRecordTeacherActivity.class);
                    startActivity(recordTeacher);
                }
                break;
            case 7:
                Intent mailbox = new Intent(getContext(), MailboxActivity.class);
                bundle.putBoolean("student_teacher",judgeStuTea());
                mailbox.putExtras(bundle);
                startActivity(mailbox);
                break;
            case 8:
                Intent modifyPassword = new Intent(getContext(), ModifyPasswordActivity.class);
                bundle.putBoolean("student_teacher",judgeStuTea());
                modifyPassword.putExtras(bundle);
                startActivity(modifyPassword);
            default:
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (judgeStuTea()) {
                    initMyMenuStu();
                } else {
                    initMyMenuTea();
                }
            }
        }).start();
        myBeanList.clear();
        ListView listView = view.findViewById(R.id.listView_my);
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        MyAdapter adapter = new MyAdapter(requireActivity(),R.layout.my_item, myBeanList);
        listView.setAdapter(adapter);//把listView与adapter绑定，之后由adapter负责显示listView里面要显示的内容
        listView.setOnItemClickListener(this);
        Button quitLogin = view.findViewById(R.id.quit_login);
        Button quit = view.findViewById(R.id.quit);
        quitLogin.setOnClickListener(this);
        quit.setOnClickListener(this);
        adapter.notifyDataSetChanged();
    }
}
package com.example.studentattend.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.studentattend.R;
import com.example.studentattend.activity.AttendRecordStudentActivity;
import com.example.studentattend.activity.AttendRecordTeacherActivity;
import com.example.studentattend.activity.LoginActivity;
import com.example.studentattend.activity.MailboxActivity;
import com.example.studentattend.activity.MainActivity;
import com.example.studentattend.activity.MobileNumber;
import com.example.studentattend.activity.ModifyPasswordActivity;
import com.example.studentattend.collector.ActivityCollector;
import com.example.studentattend.dao.MyMenu;
import com.example.studentattend.version.APKVersionCodeUtils;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private List<MyMenu> myMenuList = new ArrayList<>();

    Bundle bundle=new Bundle();
    View view;
    ListView listView;

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
                MyMenu name = new MyMenu("名字",LoginActivity.userBean.getName(),R.drawable.ic_null);
                myMenuList.add(name);
                MyMenu stuId = new MyMenu("学号",LoginActivity.userBean.getSno(),R.drawable.ic_null);
                myMenuList.add(stuId);
                MyMenu classId = new MyMenu("班级号",LoginActivity.userBean.getClassmate(),R.drawable.ic_null);
                myMenuList.add(classId);
                MyMenu system = new MyMenu("系",LoginActivity.userBean.getDepartment(),R.drawable.ic_null);
                myMenuList.add(system);
                MyMenu gender = new MyMenu("性别",LoginActivity.userBean.getSex(),R.drawable.ic_null);
                myMenuList.add(gender);
                MyMenu telephone = new MyMenu("手机号",LoginActivity.userBean.getPhone(),R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(telephone);
                MyMenu record = new MyMenu("签到记录","",R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(record);
                MyMenu mailbox = new MyMenu("邮箱",LoginActivity.userBean.getEmail(),R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(mailbox);
                MyMenu modifyPassword = new MyMenu("修改密码","",R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(modifyPassword);
                MyMenu about = new MyMenu("关于", APKVersionCodeUtils.getVerName(requireContext()),R.drawable.ic_null);
                myMenuList.add(about);
            }
        }).start();
    }

    //老师
    private void initMyMenuTea() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyMenu name = new MyMenu("名字",LoginActivity.userBean.getName(),R.drawable.ic_null);
                myMenuList.add(name);
                MyMenu teaId = new MyMenu("教工号",LoginActivity.userBean.getSno(),R.drawable.ic_null);
                myMenuList.add(teaId);
                MyMenu system = new MyMenu("系",LoginActivity.userBean.getDepartment(),R.drawable.ic_null);
                myMenuList.add(system);
                MyMenu gender = new MyMenu("性别",LoginActivity.userBean.getSex(),R.drawable.ic_null);
                myMenuList.add(gender);
                MyMenu telephone = new MyMenu("手机号",LoginActivity.userBean.getPhone(),R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(telephone);
                MyMenu record = new MyMenu("签到记录","",R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(record);
                MyMenu mailbox = new MyMenu("邮箱",LoginActivity.userBean.getEmail(),R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(mailbox);
                MyMenu modifyPassword = new MyMenu("修改密码","",R.drawable.ic_baseline_chevron_right_24);
                myMenuList.add(modifyPassword);
                MyMenu about = new MyMenu("关于", APKVersionCodeUtils.getVerName(requireContext()),R.drawable.ic_null);
                myMenuList.add(about);
            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_login:
                Intent quitLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(quitLogin);
                //结束当前activity
                ActivityCollector.finishAll(false);
                break;
            case R.id.quit:
                ActivityCollector.finishAll(false);
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
        myMenuList.clear();
        listView = view.findViewById(R.id.listView_my);
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        MyAdapter adapter = new MyAdapter(requireActivity(),R.layout.my_item,myMenuList);
        listView.setAdapter(adapter);//把listView与adapter绑定，之后由adapter负责显示listView里面要显示的内容
        listView.setOnItemClickListener(this);
        Button quitLogin = view.findViewById(R.id.quit_login);
        Button quit = view.findViewById(R.id.quit);
        quitLogin.setOnClickListener(this);
        quit.setOnClickListener(this);
        super.onResume();
    }
}
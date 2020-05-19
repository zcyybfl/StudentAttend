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
import com.example.studentattend.activity.LoginActivity;
import com.example.studentattend.activity.MailboxActivity;
import com.example.studentattend.activity.MainActivity;
import com.example.studentattend.activity.MobileNumber;
import com.example.studentattend.activity.ModifyPasswordActivity;
import com.example.studentattend.dao.MyMenu;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    //获取是老师还是学生登录
    public static String student_teacher = null;

    private List<MyMenu> myMenuList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        student_teacher = MainActivity.student_teacher;
        initMyMenu();
        ListView listView = view.findViewById(R.id.listView_my);
        //创建adapter adapter有很多种类型，这里使用最简单的类型——数组
        MyAdapter adapter = new MyAdapter(requireActivity(),R.layout.my_item,myMenuList);
        listView.setAdapter(adapter);//把listView与adapter绑定，之后由adapter负责显示listView里面要显示的内容
        listView.setOnItemClickListener(this);
        Button quitLogin = view.findViewById(R.id.quit_login);
        Button quit = view.findViewById(R.id.quit);
        quitLogin.setOnClickListener(this);
        quit.setOnClickListener(this);
        return view;
    }

    private void initMyMenu() {
        MyMenu name = new MyMenu("名字",LoginActivity.studentBean.getName(),R.drawable.ic_null);
        myMenuList.add(name);
        if (student_teacher.equals("1")) {
            MyMenu stuId = new MyMenu("学号",LoginActivity.studentBean.getSno(),R.drawable.ic_null);
            myMenuList.add(stuId);
        } else if (student_teacher.equals("2")) {
            MyMenu teaId = new MyMenu("教工号",LoginActivity.studentBean.getSno(),R.drawable.ic_null);
            myMenuList.add(teaId);
        }
        if (student_teacher.equals("1")) {
            MyMenu classId = new MyMenu("班级号",LoginActivity.studentBean.getClassmate(),R.drawable.ic_null);
            myMenuList.add(classId);
        }
        MyMenu system = new MyMenu("系",LoginActivity.studentBean.getDepartment(),R.drawable.ic_null);
        myMenuList.add(system);
        MyMenu gender = new MyMenu("性别",LoginActivity.studentBean.getSex(),R.drawable.ic_null);
        myMenuList.add(gender);
        MyMenu telephone = new MyMenu("手机号",LoginActivity.studentBean.getPhone(),R.drawable.ic_baseline_chevron_right_24);
        myMenuList.add(telephone);
        MyMenu record = new MyMenu("签到记录","",R.drawable.ic_baseline_chevron_right_24);
        myMenuList.add(record);
        MyMenu mailbox = new MyMenu("邮箱",LoginActivity.studentBean.getEmail(),R.drawable.ic_baseline_chevron_right_24);
        myMenuList.add(mailbox);
        MyMenu modifyPassword = new MyMenu("修改密码","",R.drawable.ic_baseline_chevron_right_24);
        myMenuList.add(modifyPassword);
        MyMenu about = new MyMenu("关于","1.0.0",R.drawable.ic_null);
        myMenuList.add(about);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_login:
                Intent quitLogin = new Intent(getContext(), LoginActivity.class);
                startActivity(quitLogin);
                //结束当前activity
                requireActivity().finish();
                break;
            case R.id.quit:
                requireActivity().finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MyMenu myMenu;
        Bundle bundle=new Bundle();
        if (student_teacher.equals("2")) {
            //如果当为老师时，listView的position++;
            position++;
        }
        switch (position) {
            case 5:
                Intent mobileNumber = new Intent(getContext(), MobileNumber.class);
                myMenu = myMenuList.get(position);
                //用Bundle携带数据
                bundle.putString("tel", myMenu.getData());
                mobileNumber.putExtras(bundle);
                startActivity(mobileNumber);
                break;
            case 6:
                //签到记录
                break;
            case 7:
                Intent mailbox = new Intent(getContext(), MailboxActivity.class);
                myMenu = myMenuList.get(position);
                //用Bundle携带数据
                bundle.putString("email", myMenu.getData());
                mailbox.putExtras(bundle);
                startActivity(mailbox);
                break;
            case 8:
                Intent modifyPassword = new Intent(getContext(), ModifyPasswordActivity.class);
                startActivity(modifyPassword);
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        onCreate(null);
    }
}
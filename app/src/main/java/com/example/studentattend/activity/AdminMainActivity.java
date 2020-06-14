package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.example.studentattend.R;
import com.example.studentattend.adapter.AdminFragmentAdapter;
import com.example.studentattend.collector.ActivityCollector;
import com.example.studentattend.fragment.ClassCourseFragment;
import com.example.studentattend.fragment.CourseFragment;
import com.example.studentattend.fragment.StudentFragment;
import com.example.studentattend.fragment.TeacherCourseFragment;
import com.example.studentattend.fragment.TeacherFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class AdminMainActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        //添加菜单
        setSupportActionBar(toolbar);
        initPageViewer();
    }

    private void initPageViewer() {
        List<String> titles = new ArrayList<>();
        titles.add("学生");
        titles.add("教师");
        titles.add("课程");
        titles.add("教师课程");
        titles.add("班级课程");

        tabLayout.addTab(tabLayout.newTab().setText(titles.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(2)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(3)));
        tabLayout.addTab(tabLayout.newTab().setText(titles.get(4)));

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StudentFragment());
        fragments.add(new TeacherFragment());
        fragments.add(new CourseFragment());
        fragments.add(new TeacherCourseFragment());
        fragments.add(new ClassCourseFragment());

        //默认预加载页面数量
        viewPager.setOffscreenPageLimit(4);

        AdminFragmentAdapter adapter = new AdminFragmentAdapter(
                getSupportFragmentManager(),1,fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin_quit_login:
                new AlertDialog.Builder(this)
                        .setTitle("退出登录")
                        .setMessage("您确定退出登录")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AdminMainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
                break;
            case R.id.admin_quit:
                new AlertDialog.Builder(this)
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
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
        return false;
    }
}
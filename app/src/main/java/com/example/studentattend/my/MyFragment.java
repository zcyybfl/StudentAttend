package com.example.studentattend.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentattend.R;
import com.example.studentattend.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

public class MyFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        RecyclerView myRecyclerView = view.findViewById(R.id.recyclerView_my);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myRecyclerView.setLayoutManager(layoutManager);
        MyAdapter adapter = new MyAdapter(getMyMenu());
        myRecyclerView.setAdapter(adapter);
        Button quitLogin = view.findViewById(R.id.quit_login);
        Button quit = view.findViewById(R.id.quit);
        quitLogin.setOnClickListener(this);
        quit.setOnClickListener(this);
        return view;
    }

    private List<MyMenu> getMyMenu() {
        List<MyMenu> myMenuList = new ArrayList<>();
        MyMenu name = new MyMenu("姓名","张三");
        myMenuList.add(name);
        MyMenu stuId = new MyMenu("学号","1840610610");
        myMenuList.add(stuId);
        MyMenu grander = new MyMenu("性别","男");
        myMenuList.add(grander);
        return myMenuList;
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
}
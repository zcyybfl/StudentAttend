package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceLogin extends Thread{

    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String username,String password,String flag){
        if (flag.equals("student")){
            url = "http://hsjnb.com:8889/student/login";
        }else {
            url = "http://hsjnb.com:8889/teacher/login";
        }


//        path = url + "?sno=" + username + "&password=" + password + "&flag=" + flag;
        path = url + "?sno=" + username + "&password=" + password;
        System.out.println(path);
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(path)
                    .build();
            Response response = client.newCall(request).execute();
            responseDate = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BaseBean show(){
        while (true){
            Log.d("ServiceLogin", "responseDate is " + responseDate);
            if (responseDate != null){
                baseBean = gson .fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

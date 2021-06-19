package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.TeacherRecordBean;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceTeacherInfo extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();
    TeacherRecordBean teacherRecordBean;

    public void init(String sno){
        url = "http://hsjnb.com:8889/teacher/searchCourse";
        path = url + "?sno=" + sno;
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

    public TeacherRecordBean show(){
        while (true){
            Log.d("ServiceTeacherInfo", "responseDate is " + responseDate);
            if (responseDate != null){
                teacherRecordBean = gson.fromJson(responseDate,TeacherRecordBean.class);
                responseDate = null;
                return teacherRecordBean;
            }
        }
    }
}

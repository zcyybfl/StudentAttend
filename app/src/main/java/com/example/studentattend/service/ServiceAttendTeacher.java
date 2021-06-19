package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.AttendTeacherBean;
import com.example.studentattend.dao.TeacherRecordBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAttendTeacher extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();

    public void init(String sno,String time){
        url = "http://hsjnb.com:8889/teacher/searchAttendDetail";
        path = url + "?sno=" + sno + "&date=" + time;
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

    public List<AttendTeacherBean> show(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceAttendTeacher", "responseDate is " + responseDate);
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<AttendTeacherBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    AttendTeacherBean attendTeacherBean = gson.fromJson(beans,AttendTeacherBean.class);
                    list.add(attendTeacherBean);
                }
                responseDate = null;
                return list;
            }
        }
    }
}

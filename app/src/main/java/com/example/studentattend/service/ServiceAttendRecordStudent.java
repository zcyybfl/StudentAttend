package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.example.studentattend.dao.StudentRecordBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAttendRecordStudent extends Thread{

    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();
    StudentRecordBean studentRecordBean;

    public void init(String sno){
        url = "http://hsjnb.com:8889/student/searchAttendAll";
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

    public List<StudentRecordBean> show(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceAttendRecordStudent", "responseDate is " + responseDate);
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<StudentRecordBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    StudentRecordBean studentRecordBean1 = gson.fromJson(beans,StudentRecordBean.class);
                    list.add(studentRecordBean1);
                }
                responseDate = null;
                return list;
            }
        }
    }
}

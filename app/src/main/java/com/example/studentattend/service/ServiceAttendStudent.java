package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.AttendStudentBean;
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

public class ServiceAttendStudent extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();

    public void init(String student_id, String teacher_id){
        url = "http://hsjnb.com:8889/student/searchAttendDetail";
        path = url + "?studentId=" + student_id + "&teacherId=" + teacher_id;
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

    public List<AttendStudentBean> show(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceAttendStudent", "responseDate is " + responseDate);
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<AttendStudentBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    AttendStudentBean attendStudentBean = gson.fromJson(beans,AttendStudentBean.class);
                    list.add(attendStudentBean);
                }
                responseDate = null;
                return list;
            }
        }
    }
}

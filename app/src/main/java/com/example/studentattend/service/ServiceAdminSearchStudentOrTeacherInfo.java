package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.StudentOrTeacherInquireBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAdminSearchStudentOrTeacherInfo extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();

    public void init(String sno,String flag){
        url = "http://hsjnb.com:8889/admin/searchUser";
        path = url + "?sno=" + sno + "&flag=" + flag;
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

    public List<StudentOrTeacherInquireBean> show(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceAdminSearchStudentOrTeacherInfo", "responseData is " + responseDate);
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<StudentOrTeacherInquireBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    StudentOrTeacherInquireBean studentOrTeacherInquireBean = gson.fromJson(beans,StudentOrTeacherInquireBean.class);
                    list.add(studentOrTeacherInquireBean);
                }
                responseDate = null;
                return list;
            }
        }
    }
}

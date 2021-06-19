package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAdminModifyCourseInfo extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();

    public void init(String sno,String courseName){
        url = "http://hsjnb.com:8889/admin/modifyCourse";
        path = url + "?sno=" + sno + "&courseName=" + courseName;
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

    public BaseBean show() {
        while (true) {
            Log.d("ServiceAdminModifyCourseInfo", "responseDate is " + responseDate);
            if (responseDate != null) {
                BaseBean baseBean = gson.fromJson(responseDate, BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }

    }
}

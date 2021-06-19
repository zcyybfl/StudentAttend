package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceAdminDeleteCourseInfo extends Thread{
    private String url;
    private String sno;
    private String teacherId;
    private String flag;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String sno,String teacherId,String flag){
        url = "http://hsjnb.com:8889/admin/deleteCourse";
        this.sno = sno;
        this.teacherId = teacherId;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("sno",sno)
                    .add("flag",flag)
                    .add("teacherId",teacherId)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseDate = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BaseBean show(){
        while (true){
            Log.d("ServiceAdminDeleteCourseInfo", "responseDate is " + responseDate);
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

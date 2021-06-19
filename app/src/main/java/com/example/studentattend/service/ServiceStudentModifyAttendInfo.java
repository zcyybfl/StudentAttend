package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceStudentModifyAttendInfo extends Thread{
    private String url;
    private String sno;
    private String classId;
    private String attendCode;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String sno,String classId,String attendCode){
        url = "http://hsjnb.com:8889/student/modifyAttend";
        this.sno = sno;
        this.classId = classId;
        this.attendCode = attendCode;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("sno",sno)
                    .add("classId",classId)
                    .add("attendCode",attendCode)
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
            Log.d("ServiceStudentModifyAttendInfo", "responseDate is " + responseDate);
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

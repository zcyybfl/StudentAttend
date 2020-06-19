package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceAddTeacherAttendInfo extends Thread {
    private String url;
    private String sno;
    private String time;
    private String classId;
    private String attendId;
    private String attendCode;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String sno,String time,String classId,String attendId,String attendCode){
        url = "http://zltzlt.cn:8080/studentAttend/AddTeacherAttendInfo";
        this.sno = sno;
        this.time = time;
        this.classId = classId;
        this.attendId = attendId;
        this.attendCode = attendCode;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("sno",sno)
                    .add("time",time)
                    .add("classId",classId)
                    .add("attendId",attendId)
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
            Log.d("ServiceAddTeacherAttendInfo", "responseDate is " + responseDate);
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

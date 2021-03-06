package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceModify extends Thread{

    private String url;
    private String sno;
    private String item;
    private String content;
    private String flag;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String sno,String item,String content,String flag){
        if (flag.equals("student")){
            url = "http://hsjnb.com:8889/student/modify";
        }else {
            url = "http://hsjnb.com:8889/teacher/modify";
        }
        this.sno = sno;
        this.item = item;
        this.content = content;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("sno",sno)
                    .add("item",item)
                    .add("content",content)
                    .add("flag",flag)
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
            Log.d("ServiceModify", "responseDate is " + responseDate);
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

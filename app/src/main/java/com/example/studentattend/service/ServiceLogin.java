package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceLogin extends Thread{

    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();
    BaseBean baseBean = null;



    public void init(String username,String password,String flag){
        url = "http://zltzlt.cn:8080/studentAttend/Login";
        path = url + "?username=" + username + "&password=" + password + "&flag=" + flag;
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
            Log.d("ServiceLogin", "request is " + responseDate);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BaseBean show(){
        while (true){
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                Log.d("ServiceLogin", "msg is " + baseBean.getMsg());
                Log.d("ServiceLogin", "date is " + baseBean.getDate());
                responseDate = null;
                return baseBean;
            }
        }

    }


}

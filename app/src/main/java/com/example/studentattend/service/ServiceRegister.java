package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 注册的服务类
 */
public class ServiceRegister extends Thread{

    private String url;
    private String username;
    private String password;
    private String flag;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String username,String password,String flag){
        url = "http://zltzlt.cn:8080/studentAttend/Register";
        this.username = username;
        this.password = password;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("username",username)
                    .add("password",password)
                    .add("flag",flag)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseDate = response.body().string();
            //Log.d("ServiceRegister", "request is " + responseDate);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public BaseBean show(){
        while (true){
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
//                Log.d("ServiceLogin", "msg is " + baseBean.getMsg());
//                Log.d("ServiceLogin", "date is " + baseBean.getDate());
                responseDate = null;
                return baseBean;
            }
        }

    }
}
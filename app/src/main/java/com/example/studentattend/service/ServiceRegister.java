package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 注册的服务类
 */
public class ServiceRegister extends Thread{

    private String url;
    private String responseDate = null;
    StringBuffer sb = new StringBuffer();

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String sno,String password,String name,String sex,String classmate,String phone,String department,String email,String flag){
        url = "http://zltzlt.cn:8080/studentAttend/Register?";
        sb.append("sno=").append(sno);
        sb.append("&password=").append(password);
        sb.append("&name=").append(name);
        sb.append("&sex=").append(sex);
        sb.append("&class=").append(classmate);
        sb.append("&department=").append(department);
        sb.append("&phone=").append(phone);
        sb.append("&email=").append(email);
        sb.append("&flag=").append(flag);
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),sb.toString());//编码问题
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
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }

    }
}

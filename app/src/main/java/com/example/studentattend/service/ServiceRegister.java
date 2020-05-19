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
    private String username;//学号
    private String password;//密码
    private String name;//名字
    private String sex;//性别
    private String classmate;//班级
    private String department;//系别
    private String phone;//电话
    private String email;//邮箱
    private String flag;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;

    public void init(String username,String password,String name,String sex,String classmate,String phone,String department,String email,String flag){
        url = "http://zltzlt.cn:8080/studentAttend/Register";
        this.username = username;
        this.password = password;
        this.classmate = classmate;
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.department = department;
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("sno",username)
                    .add("password",password)
                    .add("name",name)
                    .add("sex",sex)
                    .add("class",classmate)
                    .add("department",department)
                    .add("phone",phone)
                    .add("email",email)
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
                responseDate = null;
                return baseBean;
            }
        }

    }
}

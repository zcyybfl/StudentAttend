package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.BaseBean;
import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceModifyAttendStudent extends Thread{
    private String url;
    private String responseDate = null;

    Gson gson = new Gson();
    BaseBean baseBean = null;
    StringBuffer sb = new StringBuffer();

    public void init(String sno,String attend_id,String status){
        url = "http://zltzlt.cn:8080/studentAttend/Modify_Student_Attend?";
        sb.append("sno=").append(sno);
        sb.append("&attend_id=").append(attend_id);
        sb.append("&status=").append(status);

    }

    @Override
    public void run() {
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),sb.toString());//编码
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
            Log.d("ServiceModify", "responseDate is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                baseBean = gson.fromJson(responseDate,BaseBean.class);
                responseDate = null;
                return baseBean;
            }
        }
    }
}

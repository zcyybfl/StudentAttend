package com.example.studentattend.service;

import android.util.Log;

import com.example.studentattend.dao.ClassCourseInquireBean;
import com.example.studentattend.dao.CourseInquireBean;
import com.example.studentattend.dao.StudentOrTeacherInquireBean;
import com.example.studentattend.dao.TeacherCourseInquireBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServiceAdminSearchCourseInfo extends Thread{
    public static String path;
    public String url;
    public static String responseDate = null;
    public Gson gson = new Gson();

    public void init(String sno,String flag){
        url = "http://zltzlt.cn:8080/studentAttend/AdminSearchCourseInfo";
        path = url + "?sno=" + sno + "&flag=" + flag;
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

    public List<CourseInquireBean> courseInfo(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<CourseInquireBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    CourseInquireBean courseInquireBean = gson.fromJson(beans,CourseInquireBean.class);
                    list.add(courseInquireBean);
                }
                responseDate = null;
                return list;
            }
        }
    }

    public List<TeacherCourseInquireBean> teacherCourseInfo(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<TeacherCourseInquireBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    TeacherCourseInquireBean teacherCourseInquireBean = gson.fromJson(beans,TeacherCourseInquireBean.class);
                    list.add(teacherCourseInquireBean);
                }
                responseDate = null;
                return list;
            }
        }
    }

    public List<ClassCourseInquireBean> classCourseInfo(){
        JsonParser jsonParser = new JsonParser();
        while (true){
            Log.d("ServiceLogin", "request is " + responseDate);//不加会导致输错账户和密码后登不上去，暂时不知怎么解决
            if (responseDate != null){
                JsonArray jsonElements = jsonParser.parse(responseDate).getAsJsonArray();
                List<ClassCourseInquireBean> list = new ArrayList<>();
                for (JsonElement beans : jsonElements){
                    ClassCourseInquireBean classCourseInquireBean = gson.fromJson(beans,ClassCourseInquireBean.class);
                    list.add(classCourseInquireBean);
                }
                responseDate = null;
                return list;
            }
        }
    }
}

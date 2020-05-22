package com.example.studentattend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.studentattend.dao.UserInfo;

public class UserManage {

    private static UserManage instance;

    public static UserInfo userInfo;

    private UserManage() {

    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }

    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context,String username,String password,String type) {
        //Context.MODE_PRIVATE表示SharedPreferences的数据只有自己应用程序能访问。
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("用户名",username);
        editor.putString("密码",password);
        editor.putString("账户类型",type);
        editor.apply();
    }

    /**
     * 获取用户信息model
     */
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        userInfo = new UserInfo();
        userInfo.setUserName(sharedPreferences.getString("用户名",""));
        userInfo.setPassword(sharedPreferences.getString("密码",""));
        userInfo.setType(sharedPreferences.getString("账户类型",""));
        return userInfo;
    }

    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            return !TextUtils.isEmpty(userInfo.getUserName())
                    && !TextUtils.isEmpty(userInfo.getPassword())
                    && !TextUtils.isEmpty(userInfo.getType());
        }
        return false;
    }

    @SuppressLint("CommitPrefEdits")
    public void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

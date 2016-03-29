package com.example.mumuseng.application;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Mumuseng on 2016/3/25.
 */
public class MyApplication extends Application {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    public static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("config", MODE_PRIVATE);
        editor = sp.edit();
        mContext = this;
    }
    public static void spSave(String name,String value){
        editor.putString(name,value);
        editor.commit();
    }
    public static void spSave(String name,Boolean value){
        editor.putBoolean(name,value);
        editor.commit();
    }
}

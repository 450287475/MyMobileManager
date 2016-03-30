package com.example.mumuseng.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.mumuseng.service.MyNumberLocationService;

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
        startService(new Intent(this, MyNumberLocationService.class));
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(new Intent(this, MyNumberLocationService.class));
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

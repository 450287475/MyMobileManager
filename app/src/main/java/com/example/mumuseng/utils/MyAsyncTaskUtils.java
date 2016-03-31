package com.example.mumuseng.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Mumuseng on 2016/3/31.
 */
public abstract class MyAsyncTaskUtils {
    public void preExecute(){

    }
    public abstract void doInBackgroud();

    public void afterBackgroud(){

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    afterBackgroud();
            }
        }
    };
    public void execute(){
        preExecute();
        new Thread(){
            @Override
            public void run() {
                doInBackgroud();
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
}

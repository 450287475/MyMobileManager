package com.example.mumuseng.mymobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.view.Setting_item;

public class SaveSetting2Activity extends ActionBarActivity {

    private Setting_item si_saveset2_regist;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(MyApplication.mContext, new MyGestureListener());
        setContentView(R.layout.activity_save_setting2);
        si_saveset2_regist = (Setting_item) findViewById(R.id.si_saveset2_regist);
        si_saveset2_regist.setMyOclickListener(new Setting_item.MyOnclickListen() {
            @Override
            public void mCheckOnclick() {
                TelephonyManager systemService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                String simSerialNumber = systemService.getSimSerialNumber();
                MyApplication.spSave("simSerialNumber",simSerialNumber);
            }

            @Override
            public void mCancleOnclick() {
                MyApplication.spSave("simSerialNumber","");
            }
        });
        si_saveset2_regist.initSaveSet2();

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class  MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float startX = e1.getX();
            float startY = e1.getY();
            float stopX = e2.getX();
            float stopY = e2.getX();
            if(stopX-startX>100){
                System.out.println("next");
            }else if(startX-stopX>100){
                System.out.println("prev");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}

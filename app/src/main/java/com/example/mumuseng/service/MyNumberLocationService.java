package com.example.mumuseng.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.dao.NumberLocationDao;
import com.example.mumuseng.mymobilemanager.R;

/**
 * Created by Mumuseng on 2016/3/29.
 */
public class MyNumberLocationService extends Service {

    private TelephonyManager systemService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        systemService = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        systemService.listen(new MyTelephonyListener(),PhoneStateListener.LISTEN_CALL_STATE);
        System.out.println("MyNumberLocationService=====onCreate");
    }
    class MyTelephonyListener extends PhoneStateListener{

        private WindowManager mWM;
        private View view;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state){
                case TelephonyManager.CALL_STATE_IDLE:
                    hideLocationView();
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    String location = NumberLocationDao.getLocation(incomingNumber);
                    System.out.println(location);
                    Toast.makeText(MyNumberLocationService.this, location, Toast.LENGTH_SHORT).show();
                    showLocation(location);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
            }
        }

        private void hideLocationView() {
            if(mWM!=null){
                mWM.removeView(view);
            }
        }

        private void showLocation(String location) {
            mWM = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            view = View.inflate(MyNumberLocationService.this, R.layout.my_number_service_location, null);
            TextView tv_message = (TextView) view.findViewById(R.id.tv_message);
            tv_message.setText(location);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("Toast");
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            float toastx = MyApplication.sp.getFloat("toastx", 200);
            float toasty = MyApplication.sp.getFloat("toasty", 200);
            params.x= (int) toastx;
            params.y= (int) toasty;

            mWM.addView(view,params);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("MyNumberLocationService=====onDestroy");
    }
}

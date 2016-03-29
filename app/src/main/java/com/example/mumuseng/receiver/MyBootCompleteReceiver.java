package com.example.mumuseng.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telecom.TelecomManager;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.example.mumuseng.application.MyApplication;

import java.util.ArrayList;

/**
 * Created by Mumuseng on 2016/3/28.
 */
public class MyBootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean clockState = MyApplication.sp.getBoolean("clockState", false);
        if(clockState){
            String simSerialNumber = MyApplication.sp.getString("simSerialNumber", "");
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            String simSerialNumber_current = telephonyManager.getSimSerialNumber();
            if(!simSerialNumber.equals(simSerialNumber_current)){
                String saveNumber = MyApplication.sp.getString("saveNumber", "");
                SmsManager smsManager = SmsManager.getDefault();
                String message="你的手机被换卡了!";
                ArrayList<String> strings = smsManager.divideMessage(message);
                for (String str:strings) {
                    smsManager.sendTextMessage(saveNumber,null,str,null,null);
                }
            }
        }
    }
}

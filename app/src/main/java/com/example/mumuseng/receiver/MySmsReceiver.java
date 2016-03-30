package com.example.mumuseng.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.mymobilemanager.R;
import com.example.mumuseng.service.MyUpdateLocationService;

/**
 * Created by Mumuseng on 2016/3/28.
 */
public class MySmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        for (Object obj:pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String messageBody = smsMessage.getMessageBody();
            String sender = smsMessage.getOriginatingAddress();
            System.out.println(messageBody+":"+sender);
            if(MyApplication.sp.getString("saveNumber","").equals(sender)) {
                switch (messageBody) {
                    case "#*location*#":
                        getLocation(context);
                        break;
                    case "#*alarm*#":
                        playAlarm(context);
                        break;
                    case "#*wipedata*#":
                        wipedata(context);
                        break;
                    case "#*lockscreen*#":
                        lockscreen(context);

                        break;
                }
            }
        }
    }

    private void wipedata(Context context) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.wipeData(0);
    }

    private void lockscreen(Context context) {
        DevicePolicyManager devicePolicyManager =(DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        devicePolicyManager.lockNow();
        devicePolicyManager.resetPassword("123",0);
    }

    private void getLocation(Context context) {
        context.startService(new Intent(context, MyUpdateLocationService.class));
       /* new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();*/
        String longitude = MyApplication.sp.getString("longitude", "");
        String latitude = MyApplication.sp.getString("latitude", "");
        System.out.println(longitude+"=========="+latitude);

    }

    private void playAlarm(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.song);
        mediaPlayer.start();
    }
}

package com.example.mumuseng.mymobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.view.Setting_item;

public class SaveSetting2Activity extends ActionBarActivity {

    private Setting_item si_saveset2_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}

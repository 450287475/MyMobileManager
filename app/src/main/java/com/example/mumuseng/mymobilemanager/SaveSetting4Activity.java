package com.example.mumuseng.mymobilemanager;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.view.Setting_item;

public class SaveSetting4Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_setting4);
        Setting_item si_saveset4_regist = (Setting_item) findViewById(R.id.si_saveset4_regist);
        si_saveset4_regist.setMyOclickListener(new Setting_item.MyOnclickListen() {
            @Override
            public void mCheckOnclick() {
                MyApplication.spSave("clockState", true);
            }

            @Override
            public void mCancleOnclick() {
                MyApplication.spSave("clockState", false);
            }
        });
        si_saveset4_regist.initSaveSet4();
    }
}

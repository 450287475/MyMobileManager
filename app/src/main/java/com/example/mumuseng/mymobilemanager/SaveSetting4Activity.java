package com.example.mumuseng.mymobilemanager;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.receiver.MyDeviceAdminReceiver;
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

        Button bt_saveset4_openAdminManager = (Button) findViewById(R.id.bt_saveset4_openAdminManager);
        bt_saveset4_openAdminManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the activity to have the user enable our admin.
               /* Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                ComponentName componentName = new ComponentName(SaveSetting4Activity.this, MyDeviceAdminReceiver.class);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,componentName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,"~~~~~");
                startActivity(intent);*/
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);

                ComponentName mDeviceAdminSample = new ComponentName(SaveSetting4Activity.this, MyDeviceAdminReceiver.class);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,mDeviceAdminSample  );
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "hello,kitty");
                startActivityForResult(intent, 100);
            }
        });
    }
}

package com.example.mumuseng.mymobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mumuseng.application.MyApplication;

public class SaveActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        if(MyApplication.sp.getString("simSerialNumber","").equals("")){
            startActivity( new Intent(this, SaveSetting1Activity.class));
        }
        Button bt_saveset_setAgain = (Button) findViewById(R.id.bt_saveset_setAgain);
        TextView tv_saveset_saveNumber = (TextView) findViewById(R.id.tv_saveset_saveNumber);
        TextView tv_saveset_saveIcon = (TextView) findViewById(R.id.tv_saveset_saveIcon);
        tv_saveset_saveNumber.setText(MyApplication.sp.getString("saveNumber", ""));
        if(MyApplication.sp.getBoolean("clockState",false)){
            tv_saveset_saveIcon.setBackgroundResource(R.drawable.lock);
        }else {
            tv_saveset_saveIcon.setBackgroundResource(R.drawable.unlock);
        }


        bt_saveset_setAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(SaveActivity.this, SaveSetting1Activity.class));
            }
        });


    }
}

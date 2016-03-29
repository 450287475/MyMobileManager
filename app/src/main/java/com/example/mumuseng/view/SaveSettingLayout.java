package com.example.mumuseng.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.mymobilemanager.R;
import com.example.mumuseng.mymobilemanager.SaveActivity;
import com.example.mumuseng.mymobilemanager.SaveSetting1Activity;
import com.example.mumuseng.mymobilemanager.SaveSetting2Activity;
import com.example.mumuseng.mymobilemanager.SaveSetting3Activity;
import com.example.mumuseng.mymobilemanager.SaveSetting4Activity;

/**
 * Created by Mumuseng on 2016/3/28.
 */
public class SaveSettingLayout extends RelativeLayout implements View.OnClickListener{

    private ImageView iv_savesettinglayout_set1;
    private ImageView iv_savesettinglayout_set2;
    private ImageView iv_savesettinglayout_set3;
    private ImageView iv_savesettinglayout_set4;
    private ImageView iv_savesettinglayout_icon;
    private Button bt_savesettinglayout_prev;
    private Button bt_savesettinglayout_next;
    private int item;

    public SaveSettingLayout(Context context) {
        super(context);
    }

    public SaveSettingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = View.inflate(getContext(), R.layout.rl_savesetting_buttom,null);
        iv_savesettinglayout_set1 = (ImageView) view.findViewById(R.id.iv_savesettinglayout_set1);
        iv_savesettinglayout_set2 = (ImageView) view.findViewById(R.id.iv_savesettinglayout_set2);
        iv_savesettinglayout_set3 = (ImageView) view.findViewById(R.id.iv_savesettinglayout_set3);
        iv_savesettinglayout_set4 = (ImageView) view.findViewById(R.id.iv_savesettinglayout_set4);
        iv_savesettinglayout_icon = (ImageView) view.findViewById(R.id.iv_savesettinglayout_icon);
        bt_savesettinglayout_prev = (Button) view.findViewById(R.id.bt_savesettinglayout_prev);
        bt_savesettinglayout_next = (Button) view.findViewById(R.id.bt_savesettinglayout_next);
        item = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "item", -1);
        bt_savesettinglayout_next.setOnClickListener(this);
        bt_savesettinglayout_prev.setOnClickListener(this);
        switch (item){
            case 1:
                iv_savesettinglayout_set1.setImageResource(android.R.drawable.presence_online);
                iv_savesettinglayout_icon.setImageResource(R.drawable.setup1);
                bt_savesettinglayout_prev.setVisibility(GONE);
                break;
            case 2:
                iv_savesettinglayout_set2.setImageResource(android.R.drawable.presence_online);
                iv_savesettinglayout_icon.setImageResource(R.drawable.setup2);
                break;
            case 3:
                iv_savesettinglayout_set3.setImageResource(android.R.drawable.presence_online);
                iv_savesettinglayout_icon.setImageResource(R.drawable.setup3);
                break;
            case 4:
                iv_savesettinglayout_set4.setImageResource(android.R.drawable.presence_online);
                iv_savesettinglayout_icon.setImageResource(R.drawable.setup3);
                bt_savesettinglayout_next.setText("设置完成");
                break;
        }


        addView(view);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_savesettinglayout_next:
                switch (item){
                    case 1:
                        getContext().startActivity(new Intent(getContext(), SaveSetting2Activity.class));
                        break;
                    case 2:
                        String simSerialNumber = MyApplication.sp.getString("simSerialNumber", "");
                        if(simSerialNumber.isEmpty()){
                            Toast.makeText(getContext(), "请勾选绑定sim卡", Toast.LENGTH_SHORT).show();
                        }else {
                            getContext().startActivity(new Intent(getContext(), SaveSetting3Activity.class));
                        }
                        break;
                    case 3:
                        String saveNumber = SaveSetting3Activity.et_saveset3_contact.getText().toString();
                        if("".equals(saveNumber)){
                            Toast.makeText(getContext(), "请填写安全联系人", Toast.LENGTH_SHORT).show();
                        }else {
                            MyApplication.spSave("saveNumber",saveNumber);
                            getContext().startActivity(new Intent(getContext(), SaveSetting4Activity.class));
                        }
                        break;
                    case 4:
                        getContext().startActivity(new Intent(getContext(), SaveActivity.class));
                        break;
                }
                break;
            case R.id.bt_savesettinglayout_prev:
                switch (item){
                    case 2:
                        getContext().startActivity(new Intent(getContext(), SaveSetting1Activity.class));
                        break;
                    case 3:
                        getContext().startActivity(new Intent(getContext(), SaveSetting2Activity.class));
                        break;
                    case 4:
                        getContext().startActivity(new Intent(getContext(), SaveSetting3Activity.class));
                        break;
                }
                break;
        }
    }
}

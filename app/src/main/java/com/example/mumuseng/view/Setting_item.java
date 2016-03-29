package com.example.mumuseng.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.mymobilemanager.R;

/**
 * Created by Mumuseng on 2016/3/25.
 */
public class Setting_item extends RelativeLayout implements View.OnClickListener {

    private TextView tv_setting_title;
    private TextView tv_setting_content;
    private CheckBox cb_setting_flag;
    private SharedPreferences.Editor editor;
    private String sp_keyname;
    private String onContent;
    private String offContent;
    private String itemtitle;
    private MyOnclickListen myOnclickListen;

    public Setting_item(Context context) {
        super(context);
        init(null);
    }


    public Setting_item(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View view = View.inflate(getContext(), R.layout.relativelayout_setting_item, null);
        tv_setting_content = (TextView) view.findViewById(R.id.tv_setting_content);
        cb_setting_flag = (CheckBox) view.findViewById(R.id.cb_setting_flag);
        tv_setting_title = (TextView) view.findViewById(R.id.tv_setting_title);
        sp_keyname = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "sp_keyname");
        onContent = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "onContent");
        offContent = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "offContent");
        itemtitle = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "itemtitle");
        editor = MyApplication.sp.edit();

        tv_setting_title.setText(itemtitle);
        //SettingActivity页面的初始化
        boolean sp_keynameValue = MyApplication.sp.getBoolean(sp_keyname, true);
        cb_setting_flag.setChecked(sp_keynameValue);
        if (sp_keynameValue) {
            tv_setting_content.setText(onContent);
        } else {
            tv_setting_content.setText(offContent);

        }
        setOnClickListener(this);
        addView(view);
    }

    public void initSaveSet2() {
        String simSerialNumber = MyApplication.sp.getString("simSerialNumber", "");
        if (simSerialNumber.isEmpty()) {
            cb_setting_flag.setChecked(false);
            tv_setting_content.setText(offContent);
        } else {
            cb_setting_flag.setChecked(true);
            tv_setting_content.setText(onContent);
        }
    }

    public void initSaveSet4() {
        boolean clockState = MyApplication.sp.getBoolean("clockState", true);
        if (clockState) {
            cb_setting_flag.setChecked(true);
            tv_setting_content.setText(onContent);
        } else {
            cb_setting_flag.setChecked(false);
            tv_setting_content.setText(offContent);
        }
        MyApplication.spSave("clockState", clockState);
    }

    public interface MyOnclickListen {
        void mCheckOnclick();

        void mCancleOnclick();
    }

    public void setMyOclickListener(MyOnclickListen listen) {
        myOnclickListen = listen;
    }

    @Override
    public void onClick(View v) {
        if (cb_setting_flag.isChecked()) {
            cb_setting_flag.setChecked(false);
            tv_setting_content.setText(offContent);
            if (myOnclickListen == null) {
                editor.putBoolean(sp_keyname, false);
                editor.commit();
            } else {
                myOnclickListen.mCancleOnclick();
            }

        } else {
            cb_setting_flag.setChecked(true);
            tv_setting_content.setText(onContent);

            if (myOnclickListen == null) {
                editor.putBoolean(sp_keyname, true);
                editor.commit();
            } else {
                myOnclickListen.mCheckOnclick();
            }
        }
    }
}

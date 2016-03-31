package com.example.mumuseng.mymobilemanager;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mumuseng.bean.AppInfo;
import com.example.mumuseng.utils.MyAsyncTaskUtils;
import com.example.mumuseng.utils.PackageUtils;

import java.text.Format;
import java.util.ArrayList;

public class SoftManagerActivity extends ActionBarActivity {

    private TextView tv_softManager_rom;
    private TextView tv_softManager_sdcard;
    private ArrayList<AppInfo> allAppInfo;
    private ArrayList<AppInfo>userAppInfo;
    private ArrayList<AppInfo>romAppInfo;
    private ListView lv_softManager_applist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manager);
        tv_softManager_rom = (TextView) findViewById(R.id.tv_SoftManager_rom);
        tv_softManager_sdcard = (TextView) findViewById(R.id.tv_SoftManager_sdcard);
        lv_softManager_applist = (ListView) findViewById(R.id.lv_SoftManager_applist);
        tv_softManager_rom.setText("手机ROM剩余空间:\r\n"+ byteToMega(PackageUtils.getAvailableRomSize()));
        tv_softManager_sdcard.setText("手机SDCard剩余空间:\r\n" + byteToMega(PackageUtils.getAvailableSdcardSize()));
       /* new MyAsyncTaskUtils(){
            @Override
            public void doInBackgroud() {
                allAppInfo = PackageUtils.getAllAppInfo();
            }
            @Override
            public void afterBackgroud() {
                AppInfoAdapter appInfoAdapter = new AppInfoAdapter();
                lv_softManager_applist.setAdapter(appInfoAdapter);
            }
        }.execute();*/
        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] params) {
                allAppInfo = PackageUtils.getAllAppInfo();
                for (AppInfo appinfo:allAppInfo) {
                    if(appinfo.isSystem()){
                        romAppInfo.add(appinfo);
                    }else {
                        userAppInfo.add(appinfo);
                    }
                }
                return null;
            }
        }.execute();

    }

    private String byteToMega(long availableRomSize) {
        return Formatter.formatFileSize(this,availableRomSize);
    }

    class AppInfoAdapter extends BaseAdapter {

        private View view;
        private ViewHolder holder;

        @Override
        public int getCount() {
            return allAppInfo.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                holder = new ViewHolder();
                view = View.inflate(SoftManagerActivity.this, R.layout.listview_softmanager, null);
                 holder.tv_SoftManager_appLocation = (TextView) view.findViewById(R.id.tv_SoftManager_appLocation);
               holder.tv_SoftManager_appTitle = (TextView) view.findViewById(R.id.tv_SoftManager_appTitle);
               holder.iv_softmanager_icon = (ImageView) view.findViewById(R.id.iv_softmanager_icon);
                view.setTag(holder);
            }else {
                view=convertView;
                holder= (ViewHolder) view.getTag();
            }
            AppInfo appInfo = allAppInfo.get(position);
            holder.iv_softmanager_icon.setImageDrawable(appInfo.getIcon());
            holder.tv_SoftManager_appTitle.setText(appInfo.getName());
            if(appInfo.isSDcard()){
                holder.tv_SoftManager_appLocation.setText("应用安装在sd卡内");
            }else {
                holder.tv_SoftManager_appLocation.setText("应用安装在ROM内");
            }
            return view;
        }
    }
    class ViewHolder{
        TextView tv_SoftManager_appLocation;
        TextView tv_SoftManager_appTitle;
        ImageView iv_softmanager_icon;
    }
}

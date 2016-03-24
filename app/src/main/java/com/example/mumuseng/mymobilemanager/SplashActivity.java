package com.example.mumuseng.mymobilemanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.mumuseng.utils.HttpUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class SplashActivity extends ActionBarActivity {


    private static final int VERSION_INFO_OK = 1;
    private static final String URL_VERSION ="http://192.168.3.23/MobileManager" ;
    private Float current_version;
    private String applicationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Toast.makeText(SplashActivity.this, "这是新版本", Toast.LENGTH_SHORT).show();
        current_version =Float.parseFloat(getVersion()) ;
        getNewVersion();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERSION_INFO_OK:
                    HashMap<String,String>map= (HashMap<String, String>) msg.obj;
                    float new_version = Float.parseFloat(map.get("version"));
                    if(new_version>current_version){

                        update(map);
                    }
                    break;
            }
        }
    };

    private void update(final HashMap<String, String> map) {
        new AlertDialog.Builder(SplashActivity.this).setTitle("发现新版本!")
                .setMessage(map.get("info")).setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(URL_VERSION + map.get("url"), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                                + applicationName;
                        File directory = new File(path);
                        if (!directory.exists()) {
                            directory.mkdirs();
                        }
                        File file = new File(path + "/" + applicationName + ".apk");
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(bytes);
                            fos.close();
                            install(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        Toast.makeText(SplashActivity.this, "下载失败,请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(SplashActivity.this, "你并不想下载新版本", Toast.LENGTH_SHORT).show();
            }
        }).show();
    }

    private void install(File file) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        System.out.println("安装成功");
    }

    private void getNewVersion() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String path =URL_VERSION+ "/apk/versionInfo.json";
                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    urlConnection.connect();
                    int code = urlConnection.getResponseCode();
                    if (code == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        String text = HttpUtils.getTextFromInputStream(inputStream);
                        JSONObject jsonObject = new JSONObject(text);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("version",(String) jsonObject.get("version"));
                        map.put("info",(String) jsonObject.get("info"));
                        map.put("url",(String) jsonObject.get("url"));
                        System.out.println(map);
                        Message message = handler.obtainMessage();
                        message.obj=map;
                        message.what=VERSION_INFO_OK;
                        handler.sendMessage(message);
                    } else {
                        System.out.println("code!=200");
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    private String getVersion() {
        String versionName;
        PackageManager manager = getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            ApplicationInfo applicationInfo = manager.getApplicationInfo(getPackageName(), 0);
            applicationName = (String) manager.getApplicationLabel(applicationInfo);
            System.out.println(versionName + "=======" + versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return versionName;
    }
}

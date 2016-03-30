package com.example.mumuseng.mymobilemanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.utils.DebugUtils;
import com.example.mumuseng.utils.HttpUtils;
import com.example.mumuseng.utils.IOUtils;
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

public class SplashActivity extends Activity {



    private Float current_version;
    private String applicationName;

    private static final int VERSION_INFO_OK = 1;
    private static final int CODE_NOT_200 = 2;
    private static final int GOTOHOME = 3;

    private static final int INTERNET_URL_ERROR = -1;
    private static final int INTERNET_IO_ERROR = -2;
    private static final int INTERNET_JSON_ERROR = -3;
    private static final String URL_VERSION ="http://192.168.3.23/MobileManager" ;
    private TextView tv_splash_version;
    private ProgressBar pb_splash_download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        pb_splash_download = (ProgressBar) findViewById(R.id.pb_splash_download);
        current_version =Float.parseFloat(getVersion()) ;
        tv_splash_version.setText("version:" + current_version + "");
        if(MyApplication.sp.getBoolean("auto_update",true)){
            getNewVersion();
        }else {
            DebugUtils.sout("设置了不更新");
            goToHome();
        }
        initDb();

    }

    private void initDb() {
        AssetManager assets = getAssets();
        try {
            InputStream open = assets.open("naddress.db");
            File file = new File(getFilesDir(), "naddress.db");
            if(file.exists()){
                return;
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            IOUtils.fisWriteToFos(open, fileOutputStream);
            fileOutputStream.close();
            open.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                    }else {
                        Toast.makeText(SplashActivity.this, "当前为最新版本", Toast.LENGTH_SHORT).show();
                        goToHome();
                    }
                    break;
                case CODE_NOT_200:
                    DebugUtils.sout("联网失败,请检查网络设置");
                    goToHome();
                    break;
                case INTERNET_IO_ERROR:
                    DebugUtils.sout("INTERNET_IO_ERROR");
                    goToHome();
                    break;
                case INTERNET_URL_ERROR:
                    DebugUtils.sout("INTERNET_URL_ERROR");
                    goToHome();
                    break;
                case INTERNET_JSON_ERROR:
                    DebugUtils.sout("INTERNET_JSON_ERROR");
                    goToHome();
                    break;
                case GOTOHOME:
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
            }
        }
    };

    private void update(final HashMap<String, String> map) {;
        new AlertDialog.Builder(SplashActivity.this).setTitle("发现新版本!")
                .setMessage(map.get("info")).setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pb_splash_download.setVisibility(View.VISIBLE);
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(URL_VERSION + map.get("url"), new AsyncHttpResponseHandler() {
                    @Override
                    public void onProgress(long bytesWritten, long totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        pb_splash_download.setMax((int) totalSize);
                        pb_splash_download.setProgress((int) bytesWritten);
                    }

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
                            DebugUtils.sout("FileNotFoundException");
                            goToHome();
                        } catch (IOException e) {
                            e.printStackTrace();
                            DebugUtils.sout("IOException");
                            goToHome();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        goToHome();
                    }
                });

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToHome();
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
        goToHome();
    }

    private void getNewVersion() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                String path =URL_VERSION+ "/apk/versionInfo.json";
                Message message = handler.obtainMessage();
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
                        map.put("url", (String) jsonObject.get("url"));
                        System.out.println(map);

                        message.obj=map;
                        message.what=VERSION_INFO_OK;
                    } else {
                        System.out.println("code!=200");
                        message.what= CODE_NOT_200;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what= INTERNET_URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what= INTERNET_IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what= INTERNET_JSON_ERROR;
                }finally {
                    handler.sendMessage(message);
                }

            }
        }.start();
    }

    private void goToHome() {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = handler.obtainMessage();
                message.what=GOTOHOME;
                handler.sendMessage(message);
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

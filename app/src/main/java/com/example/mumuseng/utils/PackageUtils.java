package com.example.mumuseng.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.bean.AppInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mumuseng on 2016/3/31.
 */
public class PackageUtils {



    public static long getAvailableSdcardSize(){
        long availableBlocks;
        long blockSize;
        long size=-1;
        File sdcard = Environment.getExternalStorageDirectory();
        StatFs statFs = new StatFs(sdcard.getAbsolutePath());
        if(Build.VERSION.SDK_INT>=18){
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }else {
            availableBlocks=statFs.getAvailableBlocks();
            blockSize=statFs.getBlockSize();
        }
        size = availableBlocks*blockSize;

        return size;
    }
    public static long getAvailableRomSize(){
        long availableBlocks;
        long blockSize;
        long size=-1;
        File rom = Environment.getDataDirectory();
        StatFs statFs = new StatFs(rom.getAbsolutePath());
        if(Build.VERSION.SDK_INT>=18){
            availableBlocks = statFs.getAvailableBlocksLong();
            blockSize = statFs.getBlockSizeLong();
        }else {
            availableBlocks=statFs.getAvailableBlocks();
            blockSize=statFs.getBlockSize();
        }
        size = availableBlocks*blockSize;

        return size;
    }
    public static ArrayList<AppInfo> getAllAppInfo(){
        ArrayList<AppInfo> appInfos = new ArrayList<>();
        PackageManager packageManager = MyApplication.mContext.getPackageManager();
        List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
        for (ApplicationInfo info :installedApplications) {
            CharSequence label = info.loadLabel(packageManager);
            Drawable icon = info.loadIcon(packageManager);
            boolean isSDcard;
            boolean isSystem;
            if((info.flags &info.FLAG_EXTERNAL_STORAGE)!=0){
                isSDcard=true;
            }else {
                isSDcard=false;
            }
            if((info.flags &info.FLAG_SYSTEM)!=0){
                isSystem =true;
            }else {
                isSystem=false;
            }
            AppInfo appInfo = new AppInfo(label.toString(), icon, isSDcard,isSystem);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}


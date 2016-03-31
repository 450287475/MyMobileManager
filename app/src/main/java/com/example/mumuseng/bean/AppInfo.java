package com.example.mumuseng.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by Mumuseng on 2016/3/31.
 */
public class AppInfo {
    String name;
    Drawable icon;
    boolean isSDcard;

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", isSDcard=" + isSDcard +
                ", isSystem=" + isSystem +
                '}';
    }

    public boolean isSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean isSystem) {
        this.isSystem = isSystem;
    }

    public AppInfo(String name, Drawable icon, boolean isSDcard, boolean isSystem) {

        this.name = name;
        this.icon = icon;
        this.isSDcard = isSDcard;
        this.isSystem = isSystem;
    }

    boolean isSystem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isSDcard() {
        return isSDcard;
    }

    public void setIsSDcard(boolean isSDcard) {
        this.isSDcard = isSDcard;
    }

    public AppInfo() {

    }

}

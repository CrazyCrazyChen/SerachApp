package com.example.myapplication.entity;

import android.graphics.drawable.Drawable;

public class AppInfo {


    public String packageName;
    public String versionName;
    public int versionCode;
    public long insTime;
    public long upTime;
    public String appName;
    public Drawable icon;
    public long byteSize;
    public String size;


    @Override
    public String toString() {
        return "\nAppInfo{" +
                "packageName='" + packageName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", insTime=" + insTime +
                ", upTime=" + upTime +
                ", appName='" + appName + '\'' +
                ", icon=" + icon +
                ", byteSize=" + byteSize +
                ", size='" + size + '\'' +
                '}';
    }
}

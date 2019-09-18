package com.example.myapplication.entity;

import android.graphics.drawable.Drawable;

import com.example.myapplication.util.Utils;

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
                ", insTime=" + insTime +"安装日期："+ Utils.getTime(insTime) +
                ", upTime=" + upTime +
                ", appName='" + appName + '\'' +
                ", icon=" + icon +
                ", byteSize=" + byteSize +
                ", size='" + size + '\'' +
                '}';
    }
}

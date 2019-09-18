package com.example.myapplication.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<AppInfo> getAppList(Context context){
        List<AppInfo>   list = new ArrayList<AppInfo>();


        PackageManager pm = context.getPackageManager();


        List<PackageInfo> pList = pm.getInstalledPackages(0);

        for (int i = 0; i<pList.size();i++){
            PackageInfo packageInfo = pList.get(i);

            AppInfo app = new AppInfo();




        }




        return list;
    }





}

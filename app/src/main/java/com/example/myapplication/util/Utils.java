package com.example.myapplication.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.entity.AppInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {

    public static List<AppInfo> getAppList(Context context){
        List<AppInfo>   list = new ArrayList<AppInfo>();


        PackageManager pm = context.getPackageManager();


        List<PackageInfo> pList = pm.getInstalledPackages(0);

        for (int i = 0; i<pList.size();i++){
            PackageInfo packageInfo = pList.get(i);

            AppInfo app = new AppInfo();

            app.packageName = packageInfo.packageName;
            app.insTime = packageInfo.firstInstallTime;
            app.versionName = packageInfo.versionName;
            app.versionCode = packageInfo.versionCode;
            app.upTime = packageInfo.lastUpdateTime;

            app.appName = (String) packageInfo.applicationInfo.loadLabel(pm);
            app.icon = packageInfo.applicationInfo.loadIcon(pm);



            String  dir = packageInfo.applicationInfo.publicSourceDir;
            long byteSize = new File(dir).length();
            app.byteSize = byteSize;
            app.size = getSize(byteSize);
            list.add(app);


        }

        return list;
    }

    public  static  String getSize(long size){

        return new DecimalFormat("0.##").format(size*1.0/(1024*1024));
    }


    public  static String getTime(long millis){
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);

    }






}

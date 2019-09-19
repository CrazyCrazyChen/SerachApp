package com.example.myapplication.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
            if(isThirdPartyApp(packageInfo.applicationInfo) && !packageInfo.packageName.equals(context.getPackageName())) {

                AppInfo app = new AppInfo();

                app.packageName = packageInfo.packageName;
                app.insTime = packageInfo.firstInstallTime;
                app.versionName = packageInfo.versionName;
                app.versionCode = packageInfo.versionCode;
                app.upTime = packageInfo.lastUpdateTime;

                app.appName = (String) packageInfo.applicationInfo.loadLabel(pm);
                app.icon = packageInfo.applicationInfo.loadIcon(pm);


                String dir = packageInfo.applicationInfo.publicSourceDir;
                long byteSize = new File(dir).length();
                app.byteSize = byteSize;
                app.size = getSize(byteSize);
                list.add(app);
            }


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



    /**
     * 判断应用是否是第三方应用
     * @param appInfo
     * @return
     */

    public static boolean isThirdPartyApp(ApplicationInfo appInfo) {
        boolean flag = false;
        if ((appInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            // 可更新的系统应用
            flag = true;
        } else if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            // 非系统应用
            flag = true;
        }
        return flag;
    }




    public static void openPackage(Context context,String packageName){


        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);




    }


    public static void uninstallApk(Activity context, String packageName, int requestCode){

        Uri uri = Uri.parse("package:"+packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE,uri);
        context.startActivityForResult(intent,requestCode);



    }

    public static List<AppInfo> getSearchResult(List<AppInfo> list,String key){



        List<AppInfo> result = new ArrayList<>();


        for(int i= 0; i<list.size();i++){
            AppInfo app = list.get(i);
            if(app.appName.toLowerCase().contains(key.toLowerCase())){
                result.add(app);

            }

        }
        return  result;
    }



}

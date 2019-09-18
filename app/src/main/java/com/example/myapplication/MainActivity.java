package com.example.myapplication;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.adapter.IUninstall;
import com.example.myapplication.adapter.MyAdapter;
import com.example.myapplication.entity.AppInfo;
import com.example.myapplication.util.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IUninstall {

    private static final String TAG = "MainActivity" ;


    ListView lv;
    List<AppInfo> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);




        lv = findViewById(R.id.lv_main);

        adapter = new MyAdapter(this);

       // list = Utils.getAppList(this);

      //  adapter.setList(list);

        lv.setAdapter(adapter);

      //  Log.d(TAG, "onCreate: "+list.toString());


        lv.setOnItemClickListener(this);

        adapter.setUninstall(this);



        updateData();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.action_settings){
            return true;

        }
        return super.onOptionsItemSelected(item);
    }


    ProgressDialog pd;

    public void  showProgressDialog(){

        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("等着");
        pd.setMessage("再等着");
        pd.show();
    }




    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg){

            adapter.setList(list);
            adapter.notifyDataSetChanged();

            Log.d(TAG, "onCreate: "+list.toString());


            pd.dismiss();
        }
    };


    private void updateData(){

        new Thread(){

            public void run(){
                list = Utils.getAppList(MainActivity.this);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(1);




            }
        }.start();

        showProgressDialog();



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        AppInfo app = (AppInfo) parent.getItemAtPosition(position);
        Utils.openPackage(this,app.packageName);


    }



    public static final int CODE_UNINSTALL = 0;

    @Override
    public void btnOnClick(int pos, String packageName) {

        Utils.uninstallApk(this,packageName,CODE_UNINSTALL);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == CODE_UNINSTALL){
            updateData();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

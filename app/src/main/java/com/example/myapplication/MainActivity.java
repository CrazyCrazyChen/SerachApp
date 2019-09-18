package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.myapplication.adapter.MyAdapter;
import com.example.myapplication.entity.AppInfo;
import com.example.myapplication.util.Utils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

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

        list = Utils.getAppList(this);

        adapter.setList(list);

        lv.setAdapter(adapter);

        Log.d(TAG, "onCreate: "+list.toString());
    }
}

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
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.adapter.IUninstall;
import com.example.myapplication.adapter.MyAdapter;
import com.example.myapplication.entity.AppInfo;
import com.example.myapplication.util.Utils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, IUninstall, SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity" ;



    public static final String[] arr_sort = {"名称","日期","大小"};

    ListView lv;
    List<AppInfo> list;
    MyAdapter adapter;


    public static final int SORT_NAME = 0;
    public static final int SORT_DATE = 1;
    public static final int SORT_SIZE = 2;

    int currSort = SORT_NAME;
    Comparator<AppInfo> currComparator = null;


    TextView tv_sort;
    TextView tv_size;




    Comparator<AppInfo> dateComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo o1, AppInfo o2) {

            if(o1.upTime > o2.upTime){
                return 1;
            }
            if(o1.upTime == o2.upTime){
                return 0;
            }
            else {
                return -1;
            }
        }
    };



    Comparator<AppInfo> sizeComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo o1, AppInfo o2) {

            if(o1.byteSize > o2.byteSize){
                return -1;
            }
            if(o1.byteSize == o2.byteSize){
                return 0;
            }
            else {
                return 1;
            }
        }
    };


    Comparator<AppInfo> nameComparator = new Comparator<AppInfo>() {
        @Override
        public int compare(AppInfo o1, AppInfo o2) {

           return o1.appName.toLowerCase().compareTo(o2.appName.toLowerCase());


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        tv_size = findViewById(R.id.tv_size);
        tv_sort = findViewById(R.id.tv_sort);



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

    SearchView sv;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem search = menu.findItem(R.id.search);


        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                updateData();
                return true;
            }
        });

        sv = (SearchView) search.getActionView();

        sv.setSubmitButtonEnabled(true);
        sv.setQueryHint("应用名称");
        sv.setOnQueryTextListener(this);






        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();


        if (id == R.id.refresh){
            updateData();
            return true;

        }

        if (id == R.id.sort_name){

            currSort = SORT_NAME;

            updateDate_sort(currSort);
            return true;

        }

        if (id == R.id.sort_date){
            currSort = SORT_DATE;

            updateDate_sort(currSort);
            return true;

        }


        if (id == R.id.sort_size){

            currSort = SORT_SIZE;

            updateDate_sort(currSort);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }



    public void updateDate_sort(int sort){



        if(sort == SORT_NAME){
            currComparator = nameComparator;
        }
        if(sort == SORT_DATE){
            currComparator = dateComparator;
        }
        if(sort == SORT_SIZE){
            currComparator = sizeComparator;
        }
        Collections.sort(list,currComparator);
        adapter.setList(list);
        adapter.notifyDataSetChanged();

        update_top();

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

            updateDate_sort(currSort);
//            adapter.setList(list);
//            adapter.notifyDataSetChanged();

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

    @Override
    public boolean onQueryTextSubmit(String query) {


        Log.d(TAG, "onQueryTextSubmit: "+query);
        list = Utils.getSearchResult(list,query);
        updateDate_sort(currSort);




        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        return true;
    }


    public void update_top(){
        tv_sort.setText("排序："+arr_sort[currSort]);

        tv_size.setText("应用："+list.size());
    }
}

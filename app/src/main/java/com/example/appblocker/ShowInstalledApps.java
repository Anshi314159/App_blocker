package com.example.appblocker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ShowInstalledApps extends AppCompatActivity {

    RecyclerView recyclerView;

    List<AppModel> appModelList = new ArrayList<>();

    AppAdapter adapter;

    ProgressDialog progressDialog;
    Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_installed_apps);

        con = this;
        recyclerView = findViewById(R.id.applocklist);

        adapter = new AppAdapter(appModelList, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                getInstalledapps();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.setTitle("Fetching Apps");
        progressDialog.setMessage("Loading");
        progressDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void getInstalledapps(){

        List<String> list = SharedPrefUtil.getInstance(con).getListString();

        List<PackageInfo> packageInfos = getPackageManager().getInstalledPackages(0);

        // add to list of dataset

        for(int i = 0; i < packageInfos.size(); i++){

            String name = packageInfos.get(i).applicationInfo.loadLabel(getPackageManager()).toString();
            Drawable icon = packageInfos.get(i).applicationInfo.loadIcon(getPackageManager());
            String packname = packageInfos.get(i).packageName;


            if (!list.isEmpty()){

                if(list.contains(packname)){
                    appModelList.add( new AppModel( name, icon, 1, packname ));
                }
                else{
                    appModelList.add( new AppModel( name, icon, 0, packname ));
                }

            }

            else{
                appModelList.add( new AppModel( name, icon, 0, packname ));
            }

        }

        adapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }
}
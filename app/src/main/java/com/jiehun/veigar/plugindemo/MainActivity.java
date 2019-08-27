package com.jiehun.veigar.plugindemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public void loadPlugin(View view) {
        PluginManager.getInstance(this).loadPlugin();


    }

    public void startPluginActivity(View view) {

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
        String path = file.getAbsolutePath();

        //获取插件包里面的Activity
        PackageManager packageManager = getPackageManager();
        PackageInfo packageArchiveInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        ActivityInfo activityInfo = packageArchiveInfo.activities[0];

        //占位 代理Activity
        Intent intent = new Intent(this,ProxyActivity.class);
        intent.putExtra("className",activityInfo.name);
        startActivity(intent);
    }

    private void checkPermission() {
        // Storage Permissions
        final int REQUEST_EXTERNAL_STORAGE = 1;
        String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(MainActivity.this,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public void register_static_broadcast(View view) {
        PluginManager.getInstance(this).parserApkAction();

    }

    public void send_broadcast(View view) {
        Intent intent = new Intent();
        intent.setAction("plugin_static_receiver");
        sendBroadcast(intent);
    }
}

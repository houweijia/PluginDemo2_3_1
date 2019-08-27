package com.jiehun.veigar.plugin_package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import com.jiehun.veigar.stander.ActivityInterface;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/14
 */
public class BaseActivity extends Activity implements ActivityInterface {
    public Activity appActivity;

    @Override
    public void insertAppContext(Activity appActivity) {
        this.appActivity = appActivity;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onStart() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onResume() {

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onDestroy() {

    }

    public void setContentView(int resId) {
        appActivity.setContentView(resId);
    }

    public View findViewById(int layoutId){
        return appActivity.findViewById(layoutId);
    }

    public void startActivity(Intent intent){
        Intent intentNew = new Intent();
        intentNew.putExtra("className",intent.getComponent().getClassName());
        appActivity.startActivity(intentNew);
    }

    @Override
    public ComponentName startService(Intent service) {
        Intent newIntent = new Intent();
        newIntent.putExtra("className",service.getComponent().getClassName());
        return appActivity.startService(newIntent);

    }


    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        return appActivity.registerReceiver(receiver, filter);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        appActivity.sendBroadcast(intent);
    }
}

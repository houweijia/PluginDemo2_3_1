package com.jiehun.veigar.plugindemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jiehun.veigar.stander.ServiceInterface;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/20
 */
public class ProxyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String className = intent.getStringExtra("className");

        try {
            Class aClass = PluginManager.getInstance(this).getClassLoader().loadClass(className);
            Object o = aClass.newInstance();
            ServiceInterface serviceInterface = (ServiceInterface) o;

            serviceInterface.insertAppContext(this);
            serviceInterface.onStartCommand(intent,flags,startId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

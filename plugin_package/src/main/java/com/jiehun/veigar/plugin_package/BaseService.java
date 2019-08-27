package com.jiehun.veigar.plugin_package;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jiehun.veigar.stander.ServiceInterface;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/20
 */
public class BaseService extends Service implements ServiceInterface {
    private Service appService;



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void insertAppContext(Service appService) {
        this.appService = appService;
    }

    @Override
    public void onCreate() {

    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return 0;
    }

    @Override
    public void onDestroy() {

    }
}

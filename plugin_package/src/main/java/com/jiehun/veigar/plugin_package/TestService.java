package com.jiehun.veigar.plugin_package;

import android.content.Intent;
import android.util.Log;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/20
 */
public class TestService extends BaseService{

    private static final String TAG= TestService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        Log.e(TAG,"插件服务正在执行");
                    }
                }
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

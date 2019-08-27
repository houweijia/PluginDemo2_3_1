package com.jiehun.veigar.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/26
 */
public class StaticReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是静态注册的广播，我收到广播了", Toast.LENGTH_SHORT).show();
    }
}

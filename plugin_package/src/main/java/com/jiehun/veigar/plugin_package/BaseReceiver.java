package com.jiehun.veigar.plugin_package;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.jiehun.veigar.stander.ReceiverInterface;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/25
 */
public class BaseReceiver extends BroadcastReceiver implements ReceiverInterface {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "我是插件里面的广播接收者，我收到广播了", Toast.LENGTH_SHORT).show();
    }
}

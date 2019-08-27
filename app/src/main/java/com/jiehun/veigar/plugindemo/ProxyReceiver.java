package com.jiehun.veigar.plugindemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.jiehun.veigar.stander.ReceiverInterface;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/25
 */
public class ProxyReceiver extends BroadcastReceiver{

    //插件里面的BaseReceiver 全类名
    private String pluginMyReceiverClassName;

    public ProxyReceiver(String pluginMyReceiverClassName) {
        this.pluginMyReceiverClassName = pluginMyReceiverClassName;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Class receiverClass = PluginManager.getInstance(context).getClass().getClassLoader()
                    .loadClass(pluginMyReceiverClassName);
            Object o = receiverClass.newInstance();
            ReceiverInterface receiverInterface = (ReceiverInterface) o;
            receiverInterface.onReceive(context,intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

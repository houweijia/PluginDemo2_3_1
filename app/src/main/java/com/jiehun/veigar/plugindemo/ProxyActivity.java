package com.jiehun.veigar.plugindemo;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jiehun.veigar.stander.ActivityInterface;

import java.lang.reflect.Constructor;

public class ProxyActivity extends Activity {

    @Override
    public Resources getResources() {
        return PluginManager.getInstance(this).getResources();
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance(this).getClassLoader();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_proxy);
        // 真正的加载 插件里面的 Activity
        String className = getIntent().getStringExtra("className");
        try {
            Class mPluginActivityClass = getClassLoader().loadClass(className);
            Constructor constructor = mPluginActivityClass.getConstructor(new Class[]{});
            Object mPluginActivity = constructor.newInstance(new Object[]{});
            ActivityInterface activityInterface = (ActivityInterface)mPluginActivity;

            //注入
            activityInterface.insertAppContext(this);

            Bundle bundle = new Bundle();
            bundle.putString("appName","我是宿主传递过来的信息");

            //执行插件里面的onCreate方法
            activityInterface.onCreate(bundle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startActivity(Intent intent) {
        String className = intent.getStringExtra("className");

        Intent proxyIntent = new Intent(this,ProxyActivity.class);
        proxyIntent.putExtra("className",className);
        super.startActivity(proxyIntent);
    }

    @Override
    public ComponentName startService(Intent service) {
        String className = service.getStringExtra("className");

        Intent intent = new Intent(this, ProxyService.class);
        intent.putExtra("className", className);
        return super.startService(intent);
    }
}

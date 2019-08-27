package com.jiehun.veigar.plugindemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Environment;
import android.os.UserHandle;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import dalvik.system.DexClassLoader;

/**
 * @description:
 * @author: houwj
 * @date: 2019/8/14
 */
public class PluginManager {

    private static PluginManager  pluginManager;
    private        Context        context;
    private        DexClassLoader dexClassLoader;
    private        Resources      resources;

    private PluginManager(Context context) {
        this.context = context;
    }

    public static PluginManager getInstance(Context context) {
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                if (pluginManager == null) {
                    pluginManager = new PluginManager(context);
                }
            }
        }
        return pluginManager;
    }

    /**
     * （2.1 Activity class，  2.2layout）
     * 加载插件
     */
    public void loadPlugin() {

        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
            if (!file.exists()) {
                //Toast.makeText(context, "插件包不存在", Toast.LENGTH_SHORT).show();
                return;
            }

            String pluginPath = file.getAbsolutePath();
            //dexClassLoader需要一个缓存目录
            File fileDir = context.getDir("pDir", Context.MODE_PRIVATE);
            //Activity class
            dexClassLoader = new DexClassLoader(pluginPath, fileDir.getAbsolutePath(),
                    null, context.getClassLoader());

            //加载插件里面的layout
            AssetManager assetManager = AssetManager.class.newInstance();

            //执行此方法 为了把插件包的路径添加进去
            // public final int addAssetPath(String path)
            Method addAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPathMethod.invoke(assetManager, pluginPath);//插件包的路径 pluginPath

            Resources r = context.getResources();//宿主的资源配置信息
            //特殊的Resources 加载插件里面的资源 Resources
            resources = new Resources(assetManager, r.getDisplayMetrics(), r.getConfiguration());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //我们要执行此方法 为了把插件包的路径 添加进去

    }

    public ClassLoader getClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }


    public void parserApkAction() {
        try {
            // 插件包路径
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "p.apk");
            if (!file.exists()) {
                Log.d("<<<", "插件包 不存在...");
                return;
            }

            String pluginPath = file.getAbsolutePath();
            //实例化 PackageParser对象
            Class mPackageParserClass = Class.forName("android.content.pm.PackageParser");
            Object mPackageParser = mPackageParserClass.newInstance();

            // 1.执行此方法 public Package parsePackage(File packageFile, int flags)，就是为了，拿到Package
            Method parsePackageMethod = mPackageParserClass.getMethod("parsePackage", File.class, int.class);
            Object mPackage = parsePackageMethod.invoke(mPackageParser, file, PackageManager.GET_ACTIVITIES);

            Field receiversFiled = mPackage.getClass().getDeclaredField("receivers");
            // receivers 本质就是 ArrayList 集合
            ArrayList receiversList = (ArrayList) receiversFiled.get(mPackage);
            for (Object mActivity : receiversList) {
                // 获取 <intent-filter>    intents== 很多 Intent-Filter
                // 通过反射拿到 intents
                Class mComponentClass = Class.forName("android.content.pm.PackageParser$Component");
                Field intentsFiled = mComponentClass.getDeclaredField("intents");
                ArrayList<IntentFilter> intents = (ArrayList<IntentFilter>) intentsFiled.get(mActivity);

                // 我们还有一个任务，就是要拿到 android:name=".StaticReceiver"
                // activityInfo.name; == android:name=".StaticReceiver"
                // 分析源码 如何 拿到 ActivityInfo
                Class mPackageUserState = Class.forName("android.content.pm.PackageUserState");
                Class mUserHandle = Class.forName("android.os.UserHandle");
                //由于该方法是静态方法 因此不用传对象
                int userId = (int) mUserHandle.getMethod("getCallingUserId").invoke(null);
                /**
                 * 执行此方法，就能拿到 ActivityInfo
                 * public static final ActivityInfo generateActivityInfo(Activity a, int flags,
                 *             PackageUserState state, int userId)
                 */

                Method generateActivityInfoMethod = mPackageParserClass.getDeclaredMethod("generateActivityInfo",
                        mActivity.getClass(), int.class, mPackageUserState, int.class);
                ActivityInfo mActivityInfo = (ActivityInfo) generateActivityInfoMethod.invoke(null,
                        mActivity, 0, mPackageUserState.newInstance(), userId);
                String receiverClassName = mActivityInfo.name;
                Class mStaticReceiverClass = getClassLoader().loadClass(receiverClassName);
                BroadcastReceiver broadcastReceiver = (BroadcastReceiver) mStaticReceiverClass.newInstance();
                for (IntentFilter intentFilter : intents) {
                    context.registerReceiver(broadcastReceiver,intentFilter);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

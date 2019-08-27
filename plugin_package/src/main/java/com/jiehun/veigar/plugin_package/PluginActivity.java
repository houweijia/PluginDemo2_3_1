package com.jiehun.veigar.plugin_package;


import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class PluginActivity extends BaseActivity {
    private final String ACTION = "com.jiehun.veigar.plugin_package.Action";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(appActivity, "我是插件", Toast.LENGTH_SHORT).show();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(appActivity, TestActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(appActivity,TestService.class);
//                appActivity.startService(intent);
                startService(new Intent(appActivity,TestService.class));
            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter(ACTION);
                registerReceiver(new BaseReceiver(),intentFilter);

            }
        });

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(ACTION);
                sendBroadcast(intent);
            }
        });
    }

}

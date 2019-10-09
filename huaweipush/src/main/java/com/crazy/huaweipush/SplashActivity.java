package com.crazy.huaweipush;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.crazy.huaweipush.utils.CommonUtils;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;

/**
 * Created by feaoes on 2018/9/25.
 */

public class SplashActivity extends AppCompatActivity {

    public final String TAG = "SplashActivity";

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e(TAG, "onCreate......");
        setContentView(R.layout.activity_splash);

        initView();
        initPush();
        logIntent();
        logHuaWeiInfo();

    }

    private void initView() {
        findViewById(R.id.ll_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, HuaWeiPushActivity.class));
            }
        });
        textView = findViewById(R.id.tv_level);
    }

    private void logHuaWeiInfo() {
        int api = CommonUtils.getEmuiApiLevel();
        int hwid = CommonUtils.gethwid(this);
        String msg = "EMUI版本= " + api + "  华为移动服务版本= " + hwid;
        Log.e(TAG, msg);
        textView.setText(msg);
    }

    private void initPush() {
        HMSAgent.connect(this, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.e("onConnect", "HMS connect end:" + rst);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG, "onNewIntent......");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart......");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume......");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop......");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy......");
    }

    public void onLogIntent(View view) {
        logIntent();
    }

    public void logIntent() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("myscheme://com.crazy.ihuaweipush/MainActivity?message=what"));
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        String intentUri = intent.toUri(Intent.URI_INTENT_SCHEME);
        Log.e("huaweipush", "action是: " + intentUri);
    }

    public void onAutoStart(View view) {
        selfStartManagerSettingIntent(this);
    }

    private void selfStartManagerSettingIntent(Context context) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            intent = new Intent(Settings.ACTION_SETTINGS);
            context.startActivity(intent);
        }
    }


}
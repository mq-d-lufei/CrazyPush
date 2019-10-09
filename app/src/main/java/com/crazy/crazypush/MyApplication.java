package com.crazy.crazypush;

import android.app.Application;

import com.crazy.huaweipush.HWPushManager;

/**
 * Created by feaoes on 2018/9/25.
 */

public class MyApplication extends Application {

    public static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        HWPushManager.init(this);
    }

    public static MyApplication getInstance() {
        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HWPushManager.destroy();
    }
}

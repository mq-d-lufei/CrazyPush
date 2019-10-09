package com.crazy.huaweipush;

import android.app.Application;

import com.huawei.android.hms.agent.HMSAgent;

/**
 * Created by feaoes on 2018/9/25.
 */

public class MyApplication extends Application {

    public static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        HMSAgent.init(this);
    }

    public static MyApplication getInstance() {
        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HMSAgent.destroy();
    }
}

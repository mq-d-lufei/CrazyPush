package com.crazy.getui;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.crazy.getui.ginsight.GInsightEventListener;
import com.getui.gis.sdk.GInsightManager;
import com.getui.gs.sdk.GsManager;

import java.util.ArrayList;
import java.util.List;


public class DemoApplication extends Application {

    private static final String TAG = "GetuiSdkDemo";

    private static DemoHandler handler;
    public static GetuiSdkDemoActivity demoActivity;
    private List<GInsightEventListener> gInsightListeners;

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "DemoApplication onCreate");

        if (handler == null) {
            handler = new DemoHandler();
        }

        GsManager.getInstance().init(this);

        gInsightListeners = new ArrayList<>();

        GInsightManager.getInstance().init(getApplicationContext(), "sBR4eTRMeD8UMdhmST40n9");
    }

    public static void sendMessage(Message msg) {
        handler.sendMessage(msg);
    }

    public static class DemoHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    if (demoActivity != null) {
                        payloadData.append((String) msg.obj);
                        payloadData.append("\n");
                        if (GetuiSdkDemoActivity.tLogView != null) {
                            GetuiSdkDemoActivity.tLogView.append(msg.obj + "\n");
                        }
                    }
                    break;

                case 1:
                    if (demoActivity != null) {
                        if (GetuiSdkDemoActivity.tLogView != null) {
                            GetuiSdkDemoActivity.tView.setText((String) msg.obj);
                        }
                    }
                    break;
            }
        }
    }

    public void registerGInsightListener(GInsightEventListener listener) {
        gInsightListeners.add(listener);
    }

    public void unregisterGInsightListener(GInsightEventListener listener) {
        gInsightListeners.remove(listener);
    }

    public String getGiuid() {
        SharedPreferences sp = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        return sp.getString("giuid", null);
    }

    public void setGiuid(String giuid) {
        SharedPreferences sp = getSharedPreferences(getClass().getSimpleName(), Context.MODE_PRIVATE);
        sp.edit().putString("giuid", giuid).apply();

        for (GInsightEventListener l : gInsightListeners) {
            l.onGiuid(giuid);
        }
    }
}

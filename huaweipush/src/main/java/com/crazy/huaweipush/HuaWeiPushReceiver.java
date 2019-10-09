package com.crazy.huaweipush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.huawei.hms.support.api.push.PushReceiver;

import java.util.ArrayList;
import java.util.List;

/**
 * 应用需要创建一个子类继承com.huawei.hms.support.api.push.PushReceiver，
 * 实现onToken，onPushState ，onPushMsg，onEvent，这几个抽象方法，用来接收token返回，push连接状态，透传消息和通知栏点击事件处理。
 * <p>
 * onToken 调用getToken方法后，获取服务端返回的token结果，返回token以及belongId
 * onPushState 调用getPushState方法后，获取push连接状态的查询结果
 * onPushMsg 推送消息下来时会自动回调onPushMsg方法实现应用透传消息处理。本接口必须被实现。 在开发者网站上发送push消息分为通知和透传消息
 * 通知为直接在通知栏收到通知，通过点击可以打开网页，应用 或者富媒体，不会收到onPushMsg消息
 * 透传消息不会展示在通知栏，应用会收到onPushMsg
 * onEvent 该方法会在设置标签、点击打开通知栏消息、点击通知栏上的按钮之后被调用。由业务决定是否调用该函数。
 */

public class HuaWeiPushReceiver extends PushReceiver {

    public static final String TAG = "HuaWeiPushReceiver";

    public static final String ACTION_TOKEN = "action.updateToken";
    public static final String ACTION_PUSH_STATE = "action.push_state";
    public static final String ACTION_PUSH_MSG = "action.push_msg";
    public static final String ACTION_PUSH_EVENT = "action.push_event";

    private static List<IPushCallback> pushCallbacks = new ArrayList<IPushCallback>();
    private static final Object CALLBACK_LOCK = new Object();

    public interface IPushCallback {
        void onReceive(Intent intent);
    }

    public static void registerPushCallback(IPushCallback callback) {
        synchronized (CALLBACK_LOCK) {
            pushCallbacks.add(callback);
        }
    }

    public static void unRegisterPushCallback(IPushCallback callback) {
        synchronized (CALLBACK_LOCK) {
            pushCallbacks.remove(callback);
        }
    }

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String belongId = extras.getString("belongId");

        Log.e(TAG, "token: " + token + " belongId: " + belongId);

        Intent intent = new Intent();
        intent.setAction(ACTION_TOKEN);
        intent.putExtra(ACTION_TOKEN, token);
        intent.putExtra("extras", extras);
        callBack(intent);
    }

    @Override
    public void onPushState(Context context, boolean pushState) {

        Log.e(TAG, "pushState: " + pushState);
        Intent intent = new Intent();
        intent.setAction(ACTION_PUSH_STATE);
        intent.putExtra(ACTION_PUSH_STATE, pushState);
        callBack(intent);
    }


    @Override
    public boolean onPushMsg(Context context, byte[] msgBytes, Bundle extras) {
        try {
            //CP可以自己解析消息内容，然后做相应的处理 | CP can parse message content on its own, and then do the appropriate processing
            final String content = new String(msgBytes, "UTF-8");
            Log.e(TAG, "onPushMsg: content: " + content);

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MyApplication.getInstance(), content, Toast.LENGTH_LONG).show();
                }
            });

            Intent intent = new Intent();
            intent.setAction(ACTION_PUSH_MSG);
            intent.putExtra(ACTION_PUSH_MSG, content);
            intent.putExtra("extras", extras);
            callBack(intent);
        } catch (Exception e) {
            Intent intent = new Intent();
            intent.setAction(ACTION_PUSH_MSG);
            intent.putExtra("log", "Receive push pass message, exception:" + e.getMessage());
            callBack(intent);
        }
        return false;
    }

    @Override
    public void onEvent(Context context, Event event, Bundle extras) {
        Log.e(TAG, "onEvent.....");

        Intent intent = new Intent();
        intent.setAction(ACTION_PUSH_EVENT);

        int notifyId = 0;
        if (Event.NOTIFICATION_OPENED.equals(event)) {
            Log.e(TAG, "NOTIFICATION_OPENED......");
        }
        if (Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            Log.e(TAG, "NOTIFICATION_CLICK_BTN......");
        }

       /* if (Event.NOTIFICATION_OPENED.equals(event) || Event.NOTIFICATION_CLICK_BTN.equals(event)) {
            notifyId = extras.getInt(BOUND_KEY.pushNotifyId, 0);
            if (0 != notifyId) {
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (null != manager) {
                    manager.cancel(notifyId);
                }
            }
        }*/

        String message = extras.getString(BOUND_KEY.pushMsgKey);
        Log.e(TAG, "onEvent: message: " + message);

        intent.putExtra(ACTION_PUSH_EVENT, event);
        intent.putExtra("extras", extras);
        callBack(intent);
        super.onEvent(context, event, extras);
    }

    private static void callBack(Intent intent) {
        synchronized (CALLBACK_LOCK) {
            for (IPushCallback callback : pushCallbacks) {
                if (callback != null) {
                    callback.onReceive(intent);
                }
            }
        }
    }
}

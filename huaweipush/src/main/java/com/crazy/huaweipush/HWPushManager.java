package com.crazy.huaweipush;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNormalMsgHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNotifyMsgHandler;
import com.huawei.android.hms.agent.push.handler.GetPushStateHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.huawei.android.hms.agent.push.handler.QueryAgreementHandler;

/**
 * Created by feaoes on 2018/9/27.
 */

public class HWPushManager {

    public static final String TAG = "HWPushManager";

    /**
     * Application
     */
    public static void init(Application app) {
        HMSAgent.init(app);
    }

    public static void destroy() {
        HMSAgent.destroy();
    }

    /**
     * Activity
     */
    public static void connect(Activity activity) {
        HMSAgent.connect(activity, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.e(TAG, "HMS connect result code= " + rst);
            }
        });
    }

    /**
     * 获取token | get push token
     * <p>
     * rtnCode : 结果返回码
     */
    public static void getToken() {
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                Log.e(TAG, "get token result code= " + rtnCode);
            }
        });
    }

    /**
     * 删除token | delete push token
     * <p>
     * 该接口只在华为手机并且EMUI版本号不低于5.1的版本上才起作用，即只在EMUI5.1以及更高版本的华为手机上调用该接口后才不会收到PUSH消息。
     */
    public static void deleteToken(String token) {
        HMSAgent.Push.deleteToken(token, new DeleteTokenHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "deleteToken result code= " + rst);
            }
        });
    }

    /**
     * 获取push状态 | Get Push State
     */
    public static void getPushStatus() {
        HMSAgent.Push.getPushState(new GetPushStateHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "getPushState result code= " + rst);
            }
        });
    }

    /**
     * 设置是否接收普通透传消息 | Set whether to receive normal pass messages
     *
     * @param enable 是否开启 | enabled or not
     */
    public static void enableReceiveNormalMsg(boolean enable) {
        HMSAgent.Push.enableReceiveNormalMsg(enable, new EnableReceiveNormalMsgHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "enableReceiveNormalMsg result code= " + rst);
            }
        });
    }

    /**
     * 设置接收通知消息 | Set up receive notification messages
     *
     * @param enable 是否开启 | enabled or not
     */
    public static void enableReceiveNotifyMsg(boolean enable) {
        HMSAgent.Push.enableReceiveNotifyMsg(enable, new EnableReceiveNotifyMsgHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "enableReceiveNotifyMsg result code=" + rst);
            }
        });
    }

    /**
     * 显示push协议 | Show Push protocol
     */
    public static void queryAAgreement() {
        HMSAgent.Push.queryAgreement(new QueryAgreementHandler() {
            @Override
            public void onResult(int rst) {
                Log.e(TAG, "queryAgreement result code=" + rst);
            }
        });
    }

}

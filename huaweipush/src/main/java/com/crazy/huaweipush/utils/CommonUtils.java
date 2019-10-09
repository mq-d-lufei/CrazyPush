package com.crazy.huaweipush.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.lang.reflect.Method;

/**
 * Created by feaoes on 2018/9/26.
 */

public class CommonUtils {

    /**
     *
     *  1. 华为推送在EMUI 4.1 以上才能收到推送
     *  2. 华为i推送在emui 4.1到 5.0 之间是控制不了在应用内不接受通知的，5.1以上可以
     *  3. 华为推送通知栏是不叠加角标未读数的，透传可以实现！
     *  4. 华为推送需要把华为移动服务升级到最新版哦
     *  5. 新版setTag/getTag/deleteTag功能暂时不能使用
     *
     *
     */

    /**
     * 判断emuiApiLevel
     * emuiApiLevel>9即可
     */
    public static int getEmuiApiLevel() {
        int emuiApiLevel = 0;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emuiApiLevel;
    }

    /**
     * 判断华为移动服务版本
     * 判断华为移动服务版本>=2.4.1.300?
     * 判断hwid>=20401300即可
     */
    public static int gethwid(Context context) {
        PackageInfo pi = null;
        PackageManager pm = context.getPackageManager();
        int hwid = 0;
        try {
            pi = pm.getPackageInfo("com.huawei.hwid", 0);
            if (pi != null) {
                hwid = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return hwid;
    }

    /**
     * emuiApiLevel 大于0就是了
     */
    public static boolean isEMUI() {
        int emuiApiLevel = 0;
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            Method method = cls.getDeclaredMethod("get", new Class[]{String.class});
            emuiApiLevel = Integer.parseInt((String) method.invoke(cls, new Object[]{"ro.build.hw_emui_api_level"}));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emuiApiLevel > 0;
    }

}

package com.crazy.getui.ginsight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.crazy.getui.DemoApplication;
import com.getui.gis.sdk.GInsightManager;

public class GInsightEventReceiver extends BroadcastReceiver {

    public static final String TAG = GInsightEventReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getStringExtra("action");
        if (action.equalsIgnoreCase(GInsightManager.ACTION_GIUID_GENERATED)) {
            String giuid = intent.getStringExtra("giuid");
            Log.i(TAG, "giuid = " + giuid);

            DemoApplication app = (DemoApplication)context.getApplicationContext();
            app.setGiuid(giuid);
        }
    }
}

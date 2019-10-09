package com.crazy.huaweipush;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static com.crazy.huaweipush.HuaWeiPushReceiver.ACTION_TOKEN;


public class HuaWeiPushActivity extends AppCompatActivity implements View.OnClickListener, HuaWeiPushReceiver.IPushCallback {

    private final String TAG = "MainActivity";

    private String token = "";

    EditText tokenTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hua_wei_push);
        Log.e(TAG, "onCreate......");

        tokenTv = findViewById(R.id.tv_token);
        tokenTv.setText(token);

        findViewById(R.id.tv_getToken).setOnClickListener(this);
        findViewById(R.id.tv_deleteToken).setOnClickListener(this);
        findViewById(R.id.tv_getPushStatus).setOnClickListener(this);
        findViewById(R.id.tv_showAgreement).setOnClickListener(this);

        HuaWeiPushReceiver.registerPushCallback(this);
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
        HuaWeiPushReceiver.unRegisterPushCallback(this);
    }


    public void showLog(String msg) {
        Log.e(TAG, msg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_getToken) {
            HWPushManager.getToken();
        } else if (v.getId() == R.id.tv_deleteToken) {
            HWPushManager.deleteToken(token);
        } else if (v.getId() == R.id.tv_getPushStatus) {
            HWPushManager.getPushStatus();
        } else if (v.getId() == R.id.tv_showAgreement) {
            HWPushManager.queryAAgreement();
        }
    }


    @Override
    public void onReceive(Intent intent) {

        Log.e(TAG, "current : " + Thread.currentThread());

        if (intent != null) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            if (b != null && ACTION_TOKEN.equals(action)) {
                token = b.getString(ACTION_TOKEN);

                tokenTv.post(new Runnable() {
                    @Override
                    public void run() {
                        tokenTv.setText(token);
                    }
                });

            } /*else if (b != null*//* && ACTION_UPDATEUI.equals(action)*//*) {
                String log = b.getString("log");
                showLog(log);
            }*/
        }
    }


}

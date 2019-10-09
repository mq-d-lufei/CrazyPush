package com.crazy.getui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.crazy.getui.ginsight.GinsightMainActivity;
import com.crazy.getui.gssdk.GSMainActivity;

public class GeTuiActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ge_tui);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gt:
                startActivity(new Intent(this, GetuiSdkDemoActivity.class));
                break;
            case R.id.gs:
                startActivity(new Intent(this, GSMainActivity.class));

                break;
            case R.id.gx:
                startActivity(new Intent(this, GinsightMainActivity.class));

                break;
            default:
                break;
        }
    }
}
package com.crazy.getui.gssdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.crazy.getui.R;
import com.getui.gs.sdk.GsManager;


public class GSMainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText1;
    private EditText mEditText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gs_activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.btn_event_start).setOnClickListener(this);
        findViewById(R.id.btn_event_end).setOnClickListener(this);
        findViewById(R.id.bt_count).setOnClickListener(this);
        mEditText1 = findViewById(R.id.et_event1);
        mEditText2 = findViewById(R.id.et_event2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_event_start:
                if (!TextUtils.isEmpty(mEditText1.getText().toString())) {
                    GsManager.getInstance().onBeginEvent(mEditText1.getText().toString());
                } else {
                    Toast.makeText(this, "请设置自定义事件 Event ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_event_end:
                if (!TextUtils.isEmpty(mEditText1.getText().toString())) {
                    GsManager.getInstance().onEndEvent(mEditText1.getText().toString());
                } else {
                    Toast.makeText(this, "请设置自定义事件 Event ID", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_count:
                if (!TextUtils.isEmpty(mEditText2.getText().toString())) {
                    GsManager.getInstance().onEvent(mEditText2.getText().toString());
                } else {
                    Toast.makeText(this, "请设置点击事件 Event ID", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}

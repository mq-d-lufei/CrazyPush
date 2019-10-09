package com.crazy.getui.ginsight;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crazy.getui.DemoApplication;
import com.crazy.getui.R;
import com.getui.gis.sdk.GInsightManager;

public class GinsightMainActivity extends Activity implements GInsightEventListener {
    private static final String GET_TAG_HTTP_URL = "http://demo-gi.getui.com/v2/?os=android&giuid=";
    private TextView versionTextView;
    private TextView giuidTextView;
    private Button getTagBtn;
    private Button copyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ginsight_activity_main);

        versionTextView = (TextView) findViewById(R.id.versionContent);
        giuidTextView = (TextView) findViewById(R.id.giuidContent);
        getTagBtn = (Button) findViewById(R.id.getTag);
        copyBtn = (Button) findViewById(R.id.copy);

        versionTextView.setText(GInsightManager.getInstance().version());

        DemoApplication app = (DemoApplication)getApplication();
        app.registerGInsightListener(this);

        String giuid = app.getGiuid();
        if (!TextUtils.isEmpty(giuid)) {
            giuidTextView.setText(giuid);
        }

        getTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence giuid = giuidTextView.getText();
                if (!TextUtils.isEmpty(giuid)) {
                    openBrowser(Uri.parse(GET_TAG_HTTP_URL + giuid));
                } else {
                    Toast.makeText(GinsightMainActivity.this, R.string.giuid_empty_alert, Toast.LENGTH_LONG).show();
                }
            }
        });

        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy(giuidTextView.getText());
            }
        });

    }

    public void copy(CharSequence content) {
        CharSequence text = content == null ? "" : content;
        ClipboardManager cmb = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText("data", text));
    }

    private void openBrowser(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @Override
    public void onGiuid(String giuid) {
        if (!TextUtils.isEmpty(giuid)) {
            giuidTextView.setText(giuid);
        }
    }
}

package com.mirror.woodpecker.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.service.ServerService;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class ServerAlertActivity extends BaseActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunjian);
        setTitleText("新的维修单");

        intent = new Intent(ServerAlertActivity.this, ServerService.class);

        startService(intent);
        showNormalDialog("您有新的维修单","您有新的维修单", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(intent);
                finish();
            }
        });


    }
}

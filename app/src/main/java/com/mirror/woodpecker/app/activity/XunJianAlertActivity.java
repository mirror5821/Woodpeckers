package com.mirror.woodpecker.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.mirror.woodpecker.app.service.XunJianService;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianAlertActivity extends BaseActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        intent = new Intent(XunJianAlertActivity.this, XunJianService.class);
        startService(intent);
        showNormalDialog("闹钟响了", "该巡检了", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(intent);
                finish();
            }
        });
    }
}

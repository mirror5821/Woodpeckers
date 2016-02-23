package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/1/20.
 */
public class TestQQServiceActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url="mqqwpa://im/chat?chat_type=wpa&uin=839738393";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }
}

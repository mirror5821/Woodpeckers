package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setBack();
        setTitleText("登录");
    }


}

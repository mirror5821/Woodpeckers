package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class UpdatePassActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        setBack();
        setTitleText("修改密码");

    }
}

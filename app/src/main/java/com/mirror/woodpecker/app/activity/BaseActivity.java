package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.Window;

import com.mirror.woodpecker.app.model.Constants;

import dev.mirror.library.android.activity.DevBaseActivity;

/**
 * Created by mirror on 16/1/3.
 */
public class BaseActivity extends DevBaseActivity implements Constants{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        BaseActivityManager b = BaseActivityManager.getInstance();
//        b.addActivity(this);


    }
}

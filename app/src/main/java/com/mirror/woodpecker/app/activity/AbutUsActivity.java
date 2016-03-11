package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

/**
 * Created by 王沛栋 on 2016/3/11.
 */
public class AbutUsActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        loadData();
    }

    private void loadData(){

        mHttpClient.postData1(KNOW_ZMN, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

            }

            @Override
            public void onError(String msg) {

                showToast(msg);
            }
        });
    }
}

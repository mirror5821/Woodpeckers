package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.model.Types;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016-07-29.
 */
public class ServerRepairEditActivity extends BaseActivity{
    private Repair mRepair;
    private Types mTypes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRepair = getIntent().getParcelableExtra(INTENT_ID);
        mHttpClient.postData1(ORDER_TYPE, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mTypes = JsonUtils.parse(data,Types.class);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}

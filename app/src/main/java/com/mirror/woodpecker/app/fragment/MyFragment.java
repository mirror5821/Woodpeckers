package com.mirror.woodpecker.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class MyFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mHttpClient.postData1(CONSTANS, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}

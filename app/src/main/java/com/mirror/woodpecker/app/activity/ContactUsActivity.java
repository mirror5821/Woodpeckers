package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;

import dev.mirror.library.android.util.UIHelper;

/**
 * Created by 王沛栋 on 2016/3/22.
 */
public class ContactUsActivity extends BaseActivity{
    private TextView mTvPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        setBack();
        setTitleText("联系我们");
        mTvPhone = (TextView)findViewById(R.id.phone);
        mTvPhone.setOnClickListener(this);
        /*mHttpClient.postData1(CONSTANS, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });*/
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.phone:
                UIHelper.makePhoneCall(ContactUsActivity.this,getString(R.string.com_phone));
                break;
        }
    }
}

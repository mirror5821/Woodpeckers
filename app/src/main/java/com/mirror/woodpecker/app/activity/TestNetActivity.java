package com.mirror.woodpecker.app.activity;

import android.os.Bundle;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/1/5.
 */
public class TestNetActivity extends  BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("phone","18837145614");
        }catch (JSONException e){

        }
        //
        ap.postData1(Constants.GET_CODE, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                showToast("error----" + msg);
            }
        });
    }
}

package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppAjaxParam;
import com.mirror.woodpecker.app.util.AppHttpClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.x;

/**
 * Created by 王沛栋 on 2016/1/5.
 */
public class TestNetActivity extends  BaseActivity{
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn = (Button)findViewById(R.id.btn);

        mBtn.setOnClickListener(this);

//

    }



    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()){
            case R.id.btn:
                testLogin();
                break;
        }
    }


    private void testLogin2(){
        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("username", "师小兰");
            jb.put("password", "asd123456");
        }catch (JSONException e){

        }

        ap.postData1(LOGIN_TEST, jb.toString(), new AppAjaxCallback.onResultListener() {
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

    private void testLogin(){
        StringBuilder sb = new StringBuilder();
        sb.append(HOST_HEADER);
        sb.append(LOGIN_TEST);

        AppAjaxParam param = new AppAjaxParam(LOGIN_TEST,sb.toString());
        JSONObject jb = new JSONObject();
        try{
            jb.put("username", "smm");
            jb.put("password", "asd123456");
        }catch (JSONException e){

        }

        System.out.println("----------"+jb.toString());
        param.addParameter("info", jb.toString());
//        param.addParameter("username", "师小兰");
//        param.addParameter("password", "asd123456");

        x.http().post(param, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("------------" + result);
                showToast(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast(ex.getLocalizedMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

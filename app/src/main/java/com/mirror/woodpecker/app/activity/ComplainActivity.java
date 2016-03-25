package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class ComplainActivity extends BaseActivity {
    private EditText mEt;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        setBack();
        setTitleText("投诉建议");
        setRightTitle("建议列表");

        mEt = (EditText)findViewById(R.id.et);
        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                sub();
                break;
            case R.id.right_text:
                startActivity(new Intent(ComplainActivity.this,ComplainListActivity.class));
                break;
        }
    }

    private void sub(){
        String tips = mEt.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入您宝贵的建议");
            return;
        }

        JSONObject jb = new JSONObject();
        try{

            jb.put("uid", AppContext.USER_ID);
            jb.put("content",tips);
        }catch (JSONException e){

        }
        System.out.println("-------------"+jb.toString());
        mHttpClient.postData1(COMPLAIN, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
            }

            @Override
            public void onError(String msg) {

                showToast(msg);
            }
        });
    }


}

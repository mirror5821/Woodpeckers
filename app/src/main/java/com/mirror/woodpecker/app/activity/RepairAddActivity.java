package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/10.
 */
public class RepairAddActivity extends BaseActivity{
    private EditText mEtPhone,mEtDes;
    private TextView mTvLoc;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_add);
        setBack();
        setTitleText("故障申报");

        mEtPhone = (EditText)findViewById(R.id.phone);
        mEtDes = (EditText)findViewById(R.id.dec);

        mTvLoc = (TextView)findViewById(R.id.loc);
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
        }
    }

    public void sub(){
        final String phone = mEtPhone.getText().toString().trim();
        final String loc = mTvLoc.getText().toString().trim();
        final String des = mEtDes.getText().toString().trim();

        if(TextUtils.isEmpty(phone)){
            showToast("请输入联系方式");
            return;
        }

        if(TextUtils.isEmpty(loc)){
            showToast("请选择故障位置");
            return;
        }

        if(TextUtils.isEmpty(des)){
            showToast("请输入故障描述");
            return;
        }

        /**
         电话	phone
         故障位置	gz_postion
         故障描述	gz_desc
         登录用户ID		uid
         */

        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("phone", phone);
            jb.put("gz_desc", des);
            jb.put("gz_postion",loc);
            jb.put("uid", 25);

        }catch (JSONException e){

        }

        ap.postData1(REPAIR, jb.toString(), new AppAjaxCallback.onResultListener() {
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

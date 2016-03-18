package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.ImageAddsAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016/3/18.
 */
public class RepairChangeActivity extends BaseActivity{
    private EditText mEtType;
    private EditText mEtName;
    private EditText mEtDes;
    private EditText mEtPrice;
    private Button mBtn;



    private Repair mRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_change);

        setBack();
        setTitleText("维修-解决中");

        mRepair = getIntent().getParcelableExtra(INTENT_ID);

        mEtName = (EditText)findViewById(R.id.name);
        mEtDes = (EditText)findViewById(R.id.dec);
        mEtPrice = (EditText)findViewById(R.id.price);
        mEtType = (EditText)findViewById(R.id.type);
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

    private void sub(){
        String tips = mEtDes.getText().toString();
        String name = mEtName.getText().toString();
        String type = mEtType.getText().toString();
        String price = mEtPrice.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入提示语");
            return;
        }

        if(TextUtils.isEmpty(name)){
            showToast("请输入设备名称");
            return;
        }

        if(TextUtils.isEmpty(type)){
            showToast("请输入故障描述");
            return;
        }

        if(TextUtils.isEmpty(price)){
            showToast("请输入解决方案");
            return;
        }



        JSONObject jb = new JSONObject();
        try{
            /**
             *
             需调货（adjust_device）
             登录维修人员ID	uid
             要更改的状态	status               6
             订单ID	order_id
             设备名称	name
             设备型号	type
             设备价格	price
             进度提示语	tips
             */
            jb.put("order_id", mRepair.getOrder_id());
            jb.put("uid", AppContext.USER_ID);
            jb.put("status",6);
            jb.put("name",name);
            jb.put("type",type);
            jb.put("price",price);
            jb.put("action","adjust_device");
            jb.put("tips",tips);

        }catch (JSONException e){

        }


        System.out.println("-------------"+jb.toString());
        mHttpClient.postData1(ORDER_FLOW, jb.toString(), new AppAjaxCallback.onResultListener() {
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

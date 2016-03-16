package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.model.Repairman;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.UIHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/15.
 */
public class ServiceRepairActionActivity extends BaseActivity {
    private LinearLayout mViewPrice;
    private Button mBtn;
    private TextView mTv;
    private EditText mEtPrice;
    private EditText mEt;

    private Repair mRepair;
    private List<Repairman> mLists = new ArrayList<>();
    private int mRId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_repair_send);
        setBack();
        setTitleText("维修单-派单");
        mRepair = getIntent().getParcelableExtra(INTENT_ID);
        mViewPrice = (LinearLayout)findViewById(R.id.view_price);
        mBtn = (Button)findViewById(R.id.btn);
        mTv = (TextView)findViewById(R.id.tv);
        mEt = (EditText)findViewById(R.id.et);
        mEtPrice = (EditText)findViewById(R.id.price);

        //表示签约用户
        if(mRepair.getSign() == 1){
            mViewPrice.setVisibility(View.GONE);
        }else{
            mViewPrice.setVisibility(View.VISIBLE);
        }

        mBtn.setOnClickListener(this);
        mTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                upPrice();
                break;
            case R.id.tv:
                loadRepairList();
                break;
        }
    }

    private void upPrice(){
        if(TextUtils.isEmpty(mTv.getText().toString())){
            showToast("请选择维修人员");
            return;
        }
        //表示签约用户 则不用输入维修价格
        if(mRepair.getSign() == 1){
            sub();
        }else{
            String price = mEtPrice.getText().toString();
            if(TextUtils.isEmpty(price)){
                showToast("请输入维修价格");
                return;
            }

            /**
             维修价	repair_price
             订单id	order_id
             登录客服id	uid
             */
            JSONObject jb = new JSONObject();
            try{
                jb.put("order_id", mRepair.getOrder_id());
                jb.put("uid", AppContext.USER_ID);
                jb.put("repair_price",price);
                jb.put("action","price");
            }catch (JSONException e){

            }

            System.out.println("-------------"+jb.toString());
            mHttpClient.postData1(UP_REPARI_PRICE, jb.toString(), new AppAjaxCallback.onResultListener() {
                @Override
                public void onResult(String data, String msg) {
                    sub();
                }

                @Override
                public void onError(String msg) {

                    showToast(msg);
                }
            });
        }

    }

    private void sub(){
        String tips = mEt.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入维修人员");
            return;
        }
        /**
         登录客服ID、	uid
         进度提示语	tips
         订单ID	order_id
         要更改的状态	status                 3
         维修人员ID	repair_id
         */
        JSONObject jb = new JSONObject();
        try{
            jb.put("order_id", mRepair.getOrder_id());
            jb.put("uid", AppContext.USER_ID);
            jb.put("status",3);
            jb.put("tips",tips);
            jb.put("action","appoint");
            jb.put("repair_id",mRId);

           /* jb.put("order_id", mRepair.getOrder_id());
            jb.put("uid", AppContext.USER_ID);
            jb.put("status",3);
//            jb.put("tips",);
            jb.put("action","appoint");
//            jb.put("repair_id",mRId);*/
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
                System.out.println("--------------"+msg);
                showToast(msg);
            }
        });
    }

    private void loadRepairList(){
        showProgressDialog("正在加载数据");
        if(mLists.size()>0){
            initSelectView();
        }else{
            mHttpClient.postData(REPAIR_LIST, null, new AppAjaxCallback.onRecevierDataListener<Repairman>() {
                @Override
                public void onReceiverData(List<Repairman> data, String msg) {
                    mLists.addAll(data);
                    initSelectView();
                }

                @Override
                public void onReceiverError(String msg) {
                    showToast(msg);
                    cancelProgressDialog();
                }

                @Override
                public Class<Repairman> dataTypeClass() {
                    return Repairman.class;
                }
            });
        }

    }

    private void initSelectView(){
        UIHelper uiHelper = new UIHelper();
        uiHelper.initSelectUnitView(ServiceRepairActionActivity.this,mLists, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                Repairman r = mLists.get(position);
                mTv.setText(r.getNickname());
                mRId = r.getUid();

                cancelProgressDialog();
            }
        });
    }
}

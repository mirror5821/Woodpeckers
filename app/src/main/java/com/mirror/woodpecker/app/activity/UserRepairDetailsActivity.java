package com.mirror.woodpecker.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dev.mirror.library.android.util.DateUtil;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/11.
 */
public class UserRepairDetailsActivity extends BaseActivity {
    private TextView mTvNo;
    private TextView mTvPhone;
    private TextView mTvLoc;
    private TextView mTvDes;
    private TextView mTvOrderType;
    private TextView mTvOrderBelong;
    private TextView mTvOrderTypeSystem;
    private TextView mTvRepairMan;
    private TextView mTvTime;
    private TextView mTvOrderStatus;
    private EditText mEt;
    private Button mBtn;
    private LinearLayout mViewRepairMan;
    private LinearLayout mViewJindu;


    private int mOrderId;
    private Repair mRepair;

    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_details);
        mOrderId = getIntent().getIntExtra(INTENT_ID,0);
        setTitleText("维修单详情");
        setRightTitle("撤回");
        setBack();

        mInflater = getLayoutInflater();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    String [] orderStatus = {"未处理","客服关闭", "已查看", "等待接单", "已接单", "解决中", "等待调货状态", "确定调货，货已到", "已解决", "最终关闭"};

    private void initView(){
        mTvNo = (TextView)findViewById(R.id.order_no);
        mTvPhone = (TextView)findViewById(R.id.phone);
        mTvLoc = (TextView)findViewById(R.id.loc);
        mTvDes = (TextView)findViewById(R.id.dec);
        mTvOrderType = (TextView)findViewById(R.id.order_type);
        mTvOrderBelong = (TextView)findViewById(R.id.order_belong);
        mTvOrderTypeSystem = (TextView)findViewById(R.id.order_type_system);
        mTvRepairMan = (TextView)findViewById(R.id.repair_man);
        mTvTime = (TextView)findViewById(R.id.time);
        mTvOrderStatus = (TextView)findViewById(R.id.order_status);

        mEt = (EditText)findViewById(R.id.et);
        mBtn = (Button)findViewById(R.id.btn);

        mViewRepairMan = (LinearLayout)findViewById(R.id.view_repair_man);
        mViewJindu = (LinearLayout)findViewById(R.id.view_jindu);

        mTvNo.setText(mRepair.getOrder_id()+"");
        mTvPhone.setText(TextUtils.isEmpty(mRepair.getPhone())?"暂无联系方式":mRepair.getPhone());
        mTvLoc.setText(TextUtils.isEmpty(mRepair.getGz_postion())?"暂无保修位置":mRepair.getGz_postion());
        mTvDes.setText(TextUtils.isEmpty(mRepair.getGz_desc())?"暂无故障描述":mRepair.getGz_desc());
        //订单类型
        switch (mRepair.getOrder_type_id()){
            case 0:
                mTvOrderType.setText("");
                break;
            case 1:
                mTvOrderType.setText("网页");
                break;
            case 2:
                mTvOrderType.setText("电话");
                break;
            case 3:
                mTvOrderType.setText("APP");
                break;
        }
        //项目名称
        mTvOrderBelong.setText(TextUtils.isEmpty(mRepair.getProject_name()) ? "暂无故障描述" : mRepair.getProject_name());
        mTvOrderTypeSystem.setText(TextUtils.isEmpty(mRepair.getCatname()) ? "暂无数据" : mRepair.getCatname());

        if(mRepair.getRepair_id() == 0){
            mViewRepairMan.setVisibility(View.GONE);
        }else{
            mTvRepairMan.setText(mRepair.getRepairname());
        }
        mTvTime.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",mRepair.getAddtime()));

        /**
         * 订单状态
         * 0未处理  1客服关闭 2已查看 3等待接单 4已接单 5解决中 6等待调货状态 7确定调货，货已到 8已解决 9最终关闭
         */

        switch (mRepair.getOrder_status()){
            case 0:
                mTvRepairMan.setVisibility(View.GONE);
                mEt.setVisibility(View.GONE);
                mBtn.setVisibility(View.GONE);
                break;
            case 1:
                setRightTitle("评论");
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                setRightTitle("评论");
                break;
        }

        mTvOrderStatus.setText(orderStatus[mRepair.getOrder_status()]);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.right_text:
                int status = mRepair.getOrder_status();
                if(status == 1 || status == 9){
                    startActivity(new Intent(UserRepairDetailsActivity.this,CommentActivity.class).putExtra(INTENT_ID,mOrderId));
                }else{
                    //撤回订单
                    showNormalDialog("撤回维修单", "确定撤回该维修单？", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteOrder();
                        }
                    });
                }

                break;
        }
    }

    private List<String> mListJindu;
    private void loadData(){
        JSONObject jb = new JSONObject();
        try{
            jb.put("uid", AppContext.USER_ID);
            jb.put("order_id", mOrderId);
        }catch (JSONException e){

        }
        mHttpClient.postData1(ORDER_DETAILS, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mRepair = JsonUtils.parse(data,Repair.class);
                initView();

                try {
                    JSONObject jb = new JSONObject(data);
                    JSONObject jb2 = jb.getJSONObject("extinfo");


                    mListJindu = new ArrayList<>();

                    Iterator<?> iterator = jb2.keys();
                    while (iterator.hasNext()){
                        String key = (String) iterator.next();
                        //切记用optJSONObject();用getJSONObject()在为null的时候会报错哦！！！

                        String ob = String.valueOf(jb2.getString(key));
                        mListJindu.add(key);
                        mListJindu.add(ob);
                    }

                    for(int i = 1;i<=mListJindu.size()/4;i++){
                        View view = mInflater.inflate(R.layout.item_jindu,null);
                        TextView status = (TextView)view.findViewById(R.id.status);
                        TextView times = (TextView)view.findViewById(R.id.time);
                        TextView contents = (TextView)view.findViewById(R.id.content);

                        String s = mListJindu.get(i*4-4);
                        int ss = Integer.valueOf(s.substring(s.indexOf("_")+1,s.length()));
                        status.setText(orderStatus[ss]);
                        times.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm", mListJindu.get(i*4-1)));
                        contents.setText(mListJindu.get(i*4-3));
                        mViewJindu.addView(view);
                    }


                }catch (JSONException e){

                }
            }

            @Override
            public void onError(String msg) {
                System.out.println("-------------err-----"+msg);
                showToast( msg);
            }
        });
    }


    private void deleteOrder(){
        //登录id	uid
//        订单id	order_id
        JSONObject jb = new JSONObject();
        try{
            jb.put("uid", AppContext.USER_ID);
            jb.put("order_id", mOrderId);
        }catch (JSONException e){

        }
        mHttpClient.postData1(ORDER_DELETE, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                finish();
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }
}

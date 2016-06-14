package com.mirror.woodpecker.app.activity;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.fragment.XunJianRecyclerViewRefreshFragment;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Kefu;
import com.mirror.woodpecker.app.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class XunJianActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunjian_list);
        setTitleText("未巡检项目列表");
        setBack();
        XunJianRecyclerViewRefreshFragment fragment = new XunJianRecyclerViewRefreshFragment();
        Bundle b = new Bundle();
        b.putInt(INTENT_ID,0);
        fragment.setArguments(b);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment).commit();
    }

    private List<Kefu> mLists;
    private String [] orderStatus = {"未巡检","已巡检"};
    private List<Kefu> mSelector(){
        if(mLists == null){
            mLists = new ArrayList<>();
            for(int i=0;i<orderStatus.length;i++){
                Kefu k = new Kefu();
                k.setId(i);
                k.setUsername(orderStatus[i]+"项目列表");

                mLists.add(k);
            }

        }
        return mLists;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bar_title:
                UIHelper uiHelper = new UIHelper();
                uiHelper.initSelectUnitView(XunJianActivity.this,mSelector(), new DialogInterface() {
                    @Override
                    public void getPosition(int position) {
                        Kefu u = mLists.get(position);
                        XunJianRecyclerViewRefreshFragment fragment = new XunJianRecyclerViewRefreshFragment();
                        Bundle b = new Bundle();
                        b.putInt(INTENT_ID,u.getId());
                        fragment.setArguments(b);

                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_ENTER_MASK);
                        fragmentTransaction.replace(R.id.fragment_content, fragment).commit();

                        mTvTitleBar.setText(u.getUsername());
                    }
                });
                break;
        }
    }
}
/*
package com.mirror.woodpecker.app.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.XunJianUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

public class XunJianActivity extends BaseRecyclerViewActivity{

    @Override
    public int setLayoutById() {
        if(mList == null){
            mList = new ArrayList<XunJian>();
        }
        return R.layout.activity_xunjian_list;
    }

    @Override
    public void initOtherView() {
        super.initOtherView();
        setBack();
        setTitleText("巡检列表");
    }

    private boolean isXunjian = false;
    @Override
    public void loadData() {
        //页码	page
        //状态值	status    0为未巡检，1为已巡检
        JSONObject jb = new JSONObject();
        try{
            jb.put("page",1);
            jb.put("status",0);
        }catch (JSONException e){

        }
        mHttpClient.postData(XUNJIAN_LIST, jb.toString(), new AppAjaxCallback.onRecevierDataListener<XunJian>() {
            @Override
            public void onReceiverData(List<XunJian> data, String msg) {
                mList.addAll(data);

                for(int i =0;i<mList.size();i++){
                    XunJian xj = data.get(i);
                    if(xj.getStatus().equals("0")){
                        if(isXunjian){
                            break;
                        }

                        XunJianUtil.startXunjian(XunJianActivity.this);
                        isXunjian = true;
                    }
                }
                if(!isXunjian){
                    XunJianUtil.stopXunjian(XunJianActivity.this);
                }
                setAdapter();
                mRecyclerView.refreshComplete();
                mRecyclerView.setLoadingMoreEnabled(false);
                mRecyclerView.setPullRefreshEnabled(false);

            }

            @Override
            public void onReceiverError(String msg) {
                showToast(msg);
                mRecyclerView.refreshComplete();
                setAdapter();
            }

            @Override
            public Class<XunJian> dataTypeClass() {
                return XunJian.class;
            }
        });
    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_xunjian;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final XunJian xj = (XunJian)item;
        TextView name = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        TextView status = holder.getView(R.id.status);

//        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",xj.getStart_time())+" 至\n"+
//                DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",xj.getEnd_time()));
        time.setText(xj.getStart_time()+"号 至 "+xj.getEnd_time()+"号");
        name.setText(xj.getProject_name());
        if(xj.getStatus().equals("0")){
            status.setText("未巡检");
            status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
        }else{
            status.setText("已巡检");
            status.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.green));

        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xj.getStatus().equals("0")){
                    startActivity(new Intent(new Intent(XunJianActivity.this, XunJianFeedBackActivity.class).putExtra(INTENT_ID,
                            xj.getProject_id())));
                }
            }
        });
    }
}*/

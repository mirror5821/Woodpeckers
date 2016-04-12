package com.mirror.woodpecker.app.activity;


import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.XunJianUtil;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;

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
        mHttpClient.postData(XUNJIAN_LIST, null, new AppAjaxCallback.onRecevierDataListener<XunJian>() {
            @Override
            public void onReceiverData(List<XunJian> data, String msg) {
                mList.addAll(data);

                for(int i =0;i<mList.size();i++){
                    XunJian xj = data.get(i);
                    if(xj.getStatus().equals("0")){
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

        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",xj.getStart_time())+" 至\n"+
                DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",xj.getEnd_time()));
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
}
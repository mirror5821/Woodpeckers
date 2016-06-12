package com.mirror.woodpecker.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.ServiceRepairDetailsActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;

/**
 * Created by mirror on 16/1/3.
 */
public class ServiceRepairRecyclerViewRefreshFragment extends BaseRecyclerViewFragment {
    private String [] orderStatus = {"未处理","客服关闭", "已查看", "等待接单", "已接单", "解决中", "等待调货状态",
            "确定调货，货已到", "已解决", "最终关闭"};

    @Override
    public int setLayoutById() {
        if(mList == null){
            mList = new ArrayList();
        }
        return 0;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        status = getArguments().getInt(INTENT_ID);
    }

    private int status = -1;
    private String F_NAME = ORDER_LIST_SERVICE;
    @Override
    public void loadData() {
        JSONObject jb = new JSONObject();
        try{
            if(status == -1){
                jb.put("uid", AppContext.USER_ID);
                jb.put("page",pageNo);
                F_NAME = ORDER_LIST_SERVICE;
            }else{
                jb.put("uid", AppContext.USER_ID);
                jb.put("page",pageNo);
                jb.put("status",status);

                F_NAME = KEFU_LIST_BY_STATUS;
            }

        }catch (JSONException e){

        }


        mHttpClient.postData(F_NAME, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Repair>() {
            @Override
            public void onReceiverData(List<Repair> data, String msg) {
                if(status == -1){
                    if(pageNo == mDefaultPage){
                        mList.clear();
                    }
                    mList.addAll(data);
                    setAdapter();
                    mRecyclerView.setLoadingMoreEnabled(true);
                    mRecyclerView.refreshComplete();
                    mRecyclerView.loadMoreComplete();
                }else{
                    if(pageNo == mDefaultPage){
                        mList.clear();
                    }
                    mList.addAll(data);
                    setAdapter();
                    mRecyclerView.setLoadingMoreEnabled(true);
                    mRecyclerView.refreshComplete();
                    mRecyclerView.loadMoreComplete();
                }
            }

            @Override
            public void onReceiverError(String msg) {
//                mRecyclerView.noMoreLoading();
                setAdapter();
                mRecyclerView.setLoadingMoreEnabled(false);
            }

            @Override
            public Class<Repair> dataTypeClass() {
                return Repair.class;
            }
        });


    }


    @Override
    public int setItemLayoutId() {
        return R.layout.item_repair;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final Repair r = (Repair)item;
        TextView no = holder.getView(R.id.no);
        TextView time = holder.getView(R.id.time);
        TextView phone = holder.getView(R.id.phone);
        TextView dec = holder.getView(R.id.dec);
        TextView status = holder.getView(R.id.status);

        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",r.getAddtime()));
        no.setText(r.getOrder_id()+"");
        phone.setText(r.getPhone());
        dec.setText(r.getGz_desc());
        status.setText(orderStatus[r.getOrder_status()]);

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(getActivity(), ServiceRepairDetailsActivity.class).putExtra(INTENT_ID,
                        r.getOrder_id())));
            }
        });
    }
}

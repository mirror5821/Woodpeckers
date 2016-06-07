package com.mirror.woodpecker.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.UserRepairDetailsActivity;
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
public class UserRepairRecyclerViewFragment extends BaseRecyclerViewFragment {
    private String [] orderStatus = {"未处理","客服关闭", "已查看", "等待接单", "已接单", "解决中", "等待调货状态",
            "确定调货，货已到", "已解决", "最终关闭"};

    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        return 0;
    }

    private int mTypeId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTypeId = getArguments().getInt(INTENT_ID);
    }

//    @Override
//    public void addHeaderView() {
//        super.addHeaderView();
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.activity_camera,null);
//        mRecyclerView.addHeaderView(view);
//    }


    @Override
    public void loadData() {

        /**
         6.我的维修单（myorderlist）参数：用户ID 【 uid】
         7.部门维修单（departorderlist）参数：用户ID 【 uid】
         8.单位维修单（companyorderlist）参数：用户ID 【 uid】
         */
        String fName  = ORDER_LIST_MY;
        switch (mTypeId){
            case 0:
                fName  = ORDER_LIST_MY;
                break;
            case 1:
                fName  = ORDER_LIST_DEPART;
                break;
            case 2:
                fName  = ORDER_LIST_COM;
                break;
        }


        JSONObject jb = new JSONObject();
        try{
            jb.put("uid", AppContext.USER_ID);
            jb.put("page",pageNo);
        }catch (JSONException e){

        }

        mHttpClient.postData(fName, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Repair>() {
            @Override
            public void onReceiverData(List<Repair> data, String msg) {
                if(pageNo == mDefaultPage){
                    mList.clear();
                    mRecyclerView.refreshComplete();
                }
                mRecyclerView.setLoadingMoreEnabled(true);
//
                if(data.size() == 0){
                    mRecyclerView.setLoadingMoreEnabled(false);
                }else{
                    mList.addAll(data);

                }

                setAdapter();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onReceiverError(String msg) {
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
                startActivity(new Intent(new Intent(getActivity(), UserRepairDetailsActivity.class)
                        .putExtra(INTENT_ID,r.getOrder_id())));
            }
        });
    }
}

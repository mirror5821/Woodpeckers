package com.mirror.woodpecker.app.activity;

import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by 王沛栋 on 2016-07-27.
 */
public class RepairHistotyActivity extends BaseRecyclerViewActivity<Repair>{

    private int mPId;
    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("维修记录");
        setBack();
    }

    @Override
    public int setLayoutById() {
        mList = new ArrayList<>();
        return R.layout.activity_complain_list;
    }

    @Override
    public void loadData() {
        mPId = getIntent().getIntExtra(INTENT_ID,0);
        JSONObject jb = new JSONObject();
        try{
            jb.put("project_id", mPId);
        }catch (JSONException e){

        }

        mHttpClient.postData(REPAIR_HISTORY, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Repair>() {

            @Override
            public void onReceiverData(List<Repair> data, String msg) {
                mList.addAll(data);


                mRecyclerView.setLoadingMoreEnabled(false);
                mRecyclerView.setPullRefreshEnabled(false);

                setAdapter();
                mRecyclerView.loadMoreComplete();
            }

            @Override
            public void onReceiverError(String msg) {
                mRecyclerView.refreshComplete();
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
        return R.layout.item_repair_history;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final Repair repair = (Repair)item;
        TextView phone = holder.getView(R.id.phone);
        TextView loc = holder.getView(R.id.loc);
        TextView dec = holder.getView(R.id.dec);
        TextView d_dec = holder.getView(R.id.d_dec);
        TextView s_m = holder.getView(R.id.s_m);
        TextView order_type = holder.getView(R.id.order_type);
        TextView order_belong = holder.getView(R.id.order_belong);
        TextView repair_man = holder.getView(R.id.repair_man);

        phone.setText(repair.getPhone());
        loc.setText(repair.getGz_postion());
        dec.setText(repair.getGz_desc());
        d_dec.setText(repair.getDetail_desc());
        s_m.setText(repair.getSolution_method());
        order_type.setText(repair.getType_name());
        order_belong.setText(repair.getProject_name());
        repair_man.setText(repair.getRepairname());

    }
}


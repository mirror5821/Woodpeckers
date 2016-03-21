package com.mirror.woodpecker.app.fragment;

import android.content.Intent;
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
public class ServiceRepairRecyclerViewFragment extends BaseRecyclerViewFragment {
    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        return R.layout.fragment_base_rview;
    }


    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("维修单列表");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }



    @Override
    public void loadData() {

        JSONObject jb = new JSONObject();
        try{
            jb.put("uid", AppContext.USER_ID);
            jb.put("page",pageNo);
        }catch (JSONException e){

        }

        mHttpClient.postData(ORDER_LIST_SERVICE, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Repair>() {
            @Override
            public void onReceiverData(List<Repair> data, String msg) {
                if(pageNo == mDefaultPage){
                    mList.clear();
                }
                mList.addAll(data);
                setAdapter();

                mRecyclerView.refreshComplete();
            }

            @Override
            public void onReceiverError(String msg) {
                setAdapter();
//                showToast(msg);
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

        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",r.getAddtime()));
        no.setText(r.getOrder_id()+"");
        phone.setText(r.getPhone());
        dec.setText(r.getGz_desc());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(new Intent(getActivity(), ServiceRepairDetailsActivity.class).putExtra(INTENT_ID,
                        r.getOrder_id())));
            }
        });
    }
}

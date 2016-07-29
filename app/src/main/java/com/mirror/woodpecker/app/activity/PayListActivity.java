package com.mirror.woodpecker.app.activity;

import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.PayListImageAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Pay;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016-07-26.
 */
public class PayListActivity extends BaseRecyclerViewActivity<Pay>{
    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("付款凭证列表");
        setBack();
    }

    @Override
    public int setLayoutById() {
        mList = new ArrayList<>();
        return R.layout.activity_complain_list;
    }

    @Override
    public void loadData() {
        JSONObject jb = new JSONObject();
        try{
            //登录用户ID	uid
            //页码	page
            jb.put("uid", AppContext.USER_ID);
            jb.put("page",pageNo);
        }catch (JSONException e){

        }

        mHttpClient.postData(PAY_LIST, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Pay>() {

            @Override
            public void onReceiverData(List<Pay> data, String msg) {
                if(pageNo == mDefaultPage){
                    mList.clear();
                    mRecyclerView.refreshComplete();
                }
                mList.addAll(data);


                mRecyclerView.setLoadingMoreEnabled(true);
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
            public Class<Pay> dataTypeClass() {
                return Pay.class;
            }
        });

    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_pay;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final Pay pay = (Pay)item;
        NoScrollGridView gView = holder.getView(R.id.gridview);
        TextView userName = holder.getView(R.id.username);
        TextView serverName = holder.getView(R.id.server_name);
        TextView phone = holder.getView(R.id.phone);
        TextView price = holder.getView(R.id.price);
        TextView time = holder.getView(R.id.time);

        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",pay.getAddtime()));
        userName.setText(pay.getUsername());
        serverName.setText(pay.getPlayname());
        phone.setText(pay.getPhone());
        price.setText("￥"+pay.getMoney());
        if(pay.getPayimgs() != null)
            gView.setAdapter(new PayListImageAdapter(getApplicationContext(),pay.getPayimgs()));

    }
}


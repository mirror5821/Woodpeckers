package com.mirror.woodpecker.app.activity;

import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.XunJian2;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;

/**
 * Created by 王沛栋 on 2016-07-27.
 */
public class XunJianHistotyActivity extends BaseRecyclerViewActivity<XunJian2>{

    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("巡检记录");
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
            jb.put("page", pageNo);
        }catch (JSONException e){

        }

        mHttpClient.postData(XUNJIAN_HISTORY, jb.toString(), new AppAjaxCallback.onRecevierDataListener<XunJian2>() {

            @Override
            public void onReceiverData(List<XunJian2> data, String msg) {
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
            public Class<XunJian2> dataTypeClass() {
                return XunJian2.class;
            }
        });

    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_xunjian_history;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final XunJian2 xunjian = (XunJian2)item;
        TextView name = holder.getView(R.id.name);
        TextView time = holder.getView(R.id.time);
        ImageView img = holder.getView(R.id.img);


        name.setText(xunjian.getProject_name());
        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",xunjian.getAddtime()));
        AppContext.displayImage(img,"http://zmnyw.cn"+xunjian.getUrl());
    }
}


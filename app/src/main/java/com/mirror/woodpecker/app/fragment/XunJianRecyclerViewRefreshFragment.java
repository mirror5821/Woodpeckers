package com.mirror.woodpecker.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.XunJianFeedBackActivity;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.XunJianUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by mirror on 16/1/3.
 */
public class XunJianRecyclerViewRefreshFragment extends BaseRecyclerViewFragment {

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

    private boolean isXunjian = false;
    private int status = 0;
    @Override
    public void loadData() {
        //页码	page
        //状态值	status    0为未巡检，1为已巡检
        JSONObject jb = new JSONObject();
        try{
            jb.put("page",pageNo);
            jb.put("status",status);
        }catch (JSONException e){

        }
        mHttpClient.postData(XUNJIAN_LIST, jb.toString(), new AppAjaxCallback.onRecevierDataListener<XunJian>() {
            @Override
            public void onReceiverData(List<XunJian> data, String msg) {
                if(pageNo == mDefaultPage){
                    mList.clear();
                    mRecyclerView.refreshComplete();
                }
                mList.addAll(data);

                for(int i =0;i<mList.size();i++){
                    XunJian xj = data.get(i);
                    if(xj.getStatus().equals("0")){
                        if(isXunjian){
                            break;
                        }

                        XunJianUtil.startXunjian(getActivity());
                        isXunjian = true;
                    }
                }
                if(!isXunjian){
                    XunJianUtil.stopXunjian(getActivity());
                }

                mRecyclerView.setLoadingMoreEnabled(true);
                setAdapter();
                mRecyclerView.loadMoreComplete();

//                mRecyclerView.setLoadingMoreEnabled(false);
//                mRecyclerView.setPullRefreshEnabled(false);

            }

            @Override
            public void onReceiverError(String msg) {
                mRecyclerView.refreshComplete();
                setAdapter();
                mRecyclerView.setLoadingMoreEnabled(false);
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
            status.setTextColor(ContextCompat.getColor(getActivity(),R.color.red));
        }else{
            status.setText("已巡检");
            status.setTextColor(ContextCompat.getColor(getActivity(),R.color.green));

        }

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xj.getStatus().equals("0")){
                    startActivity(new Intent(new Intent(getActivity(), XunJianFeedBackActivity.class).putExtra(INTENT_ID,
                            xj.getProject_id())));
                }
            }
        });
    }
}

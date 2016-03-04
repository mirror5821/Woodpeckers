package com.mirror.woodpecker.app.activity;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;

import java.util.ArrayList;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by 王沛栋 on 2016/1/12.
 */
public class TestRecyclerViewActivity extends BaseRecyclerViewActivity {
    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        return 0;
    }

    @Override
    public void addHeaderView() {
        super.addHeaderView();
        View view = LayoutInflater.from(this).inflate(R.layout.activity_camera,null);
        mRecyclerView.addHeaderView(view);
    }

    @Override
    public void loadData() {
        if(pageNo == mDefaultPage){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mList.clear();

                    for (int i = 0; i < 15; i++) {
                        mList.add("item"+i);
                    }
                    setAdapter();

                    mRecyclerView.refreshComplete();
                }
            },5000);

        }else{
            for (int i = 0;i<15;i++){
                mList.add("item"+(i+mList.size()));
            }
            mRecyclerView.loadMoreComplete();
        }
    }

    @Override
    public int setItemLayoutId() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, final Object item) {
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast((String)item);
            }
        });
        TextView tv = holder.getView(android.R.id.text1);
        tv.setText((String)item);
    }
}

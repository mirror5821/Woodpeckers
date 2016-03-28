package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.About;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class AboutUsListViewActivity extends BaseRecyclerViewActivity{
    private List<About.Other> mDatas;
    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        mDatas = getIntent().getParcelableArrayListExtra(INTENT_ID);
        return R.layout.activity_base_gridview;
    }

    @Override
    public void loadData() {
        mList.addAll(mDatas);


        setAdapter();
        mRecyclerView.refreshComplete();
        //不允许加载更多
        mRecyclerView.setLoadingMoreEnabled(false);
        mRecyclerView.setPullRefreshEnabled(false);
    }

    @Override
    public void initOtherView() {
        super.initOtherView();
        setBack();
        setTitleText(getIntent().getStringExtra("TITLE"));
    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_about;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final About.Other data = (About.Other)item;
        TextView tv = holder.getView(android.R.id.text1);
        tv.setText(data.getTitle());


        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsListViewActivity.this, NormalWebViewActivity.class).
                        putExtra(INTENT_ID, data.getAurl()).putExtra("TITLE", data.getTitle()));
            }
        });
    }
}

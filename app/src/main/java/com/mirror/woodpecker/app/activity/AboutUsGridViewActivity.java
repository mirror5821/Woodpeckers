package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.About;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.view.SquareImageView;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class AboutUsGridViewActivity extends BaseRecyclerViewActivity{
    private List<About.Other> mDatas;
    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        mDatas = getIntent().getParcelableArrayListExtra(INTENT_ID);
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
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
        return R.layout.item_about_img;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final About.Other data = (About.Other)item;

        SquareImageView img = holder.getView(R.id.img);

        AppContext.displayImage(img, "http://zmnyw.cn"+data.getImg());

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsGridViewActivity.this, NormalWebViewActivity.class).
                        putExtra(INTENT_ID, data.getAurl()).putExtra("TITLE", data.getTitle()));
//                startActivity(new Intent(AboutUsGridViewActivity.this, AboutUsDetailsActivity.class).
//                        putExtra(INTENT_ID, data.getInfo().getContent()).putExtra("TITLE", "成功案例"));
            }
        });
    }
}

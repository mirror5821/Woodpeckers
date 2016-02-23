package com.mirror.woodpecker.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.adapter.DevRecycerViewAdapter;
import dev.mirror.library.android.xrecyclerview.ProgressStyle;
import dev.mirror.library.android.xrecyclerview.XRecyclerView;

/**
 * Created by dongqian on 16/1/3.
 */
public class TestListFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    private DVAdapter mAdapter;
    private List<String> mList = new ArrayList<>();

    private XRecyclerView mRecyclerView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mAdapter == null){
            mRecyclerView =(XRecyclerView)view.findViewById(R.id.recyclerview);
            for (int i = 0;i<15;i++){
                mList.add("item"+i);
            }
            mAdapter = new DVAdapter(getActivity(),mList,android.R.layout.simple_list_item_1);

            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(R.mipmap.ic_downgrey);

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    mList.clear();
                    for (int i = 0;i<15;i++){
                        mList.add("item"+i);
                    }

                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.refreshComplete();
                }

                @Override
                public void onLoadMore() {
                    for (int i = 0;i<15;i++){
                        mList.add("item"+i);
                    }
                    mAdapter.notifyDataSetChanged();
                    mRecyclerView.loadMoreComplete();
                }
            });
        }
    }

    class DVAdapter extends DevRecycerViewAdapter<String>{

        /**
         * 构造器
         *
         * @param context
         * @param list
         * @param layoutId
         */
        public DVAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(DevRecyclerViewHolder holder, String item) {
            TextView tv = holder.getView(android.R.id.text1);
            tv.setText(item);
        }
    }
}

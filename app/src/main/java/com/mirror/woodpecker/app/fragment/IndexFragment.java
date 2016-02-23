package com.mirror.woodpecker.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.TestAdapter;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.xrecyclerview.ProgressStyle;
import dev.mirror.library.android.xrecyclerview.XRecyclerView;

/**
 * Created by dongqian on 16/1/3.
 */
public class IndexFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_recyclerview;
    }

    private XRecyclerView mRecyclerView;
    private List<String> mList;

    private TestAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mAdapter == null){
            mRecyclerView = (XRecyclerView)view.findViewById(R.id.recyclerview);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(R.mipmap.ic_downgrey);

            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    refreshTime++;
                    times = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mList.clear();
                            for (int i = 0; i < 15; i++) {
                                mList.add("item" + i + "after" + refreshTime + "times of refresh");
                            }
                            mAdapter.notifyDataSetChanged();
                            mRecyclerView.refreshComplete();
                        }
                    }, 1000);
                }

                @Override
                public void onLoadMore() {
                    if (times < 2) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mRecyclerView.loadMoreComplete();
                                for (int i = 0; i < 15; i++) {
                                    mList.add("item" + (i + mList.size()));
                                }
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.refreshComplete();
                            }
                        }, 1000);
                    } else {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                mRecyclerView.loadMoreComplete();
                            }
                        }, 1000);
                    }
                    times++;
                }
            });

            mList = new ArrayList<>();

            for (int i=0;i<15;i++){
                mList.add("item"+(i+mList.size()));
            }

            mAdapter = new TestAdapter(getActivity(),mList);
            mRecyclerView.setAdapter(mAdapter);

        }
    }
}

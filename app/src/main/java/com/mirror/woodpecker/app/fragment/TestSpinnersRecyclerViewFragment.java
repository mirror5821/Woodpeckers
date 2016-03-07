package com.mirror.woodpecker.app.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mirror.woodpecker.app.model.TestSpinner;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by 王沛栋 on 2016/3/7.
 */
public class TestSpinnersRecyclerViewFragment extends BaseSpinnersRecyclerViewFragment {
    @Override
    public List setSpinnerData1() {
        List<TestSpinner> data = new ArrayList<>();
        for(int i=0;i<5;i++){
            TestSpinner t = new TestSpinner();
            t.setId(i);
            t.setName("测试" + i);
            data.add(t);
        }
        return data;
    }

    @Override
    public List setSpinnerData2() {
        List<TestSpinner> data = new ArrayList<>();
        for(int i=0;i<5;i++){
            TestSpinner t = new TestSpinner();
            t.setId(i);
            t.setName("呵呵" + i);
            data.add(t);
        }
        return data;
    }

    @Override
    public void Spinner2OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
//        showToast("你的选择是：" + ((TextView) arg1).getText());
    }

    @Override
    public void Spinner1OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
//        showToast("你的选择是：" + ((TextView) arg1).getText());
    }

    @Override
    public void loadData2() {
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
    public int setItemLayoutId2() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void setItemView2(DevRecyclerViewHolder holder, final Object item) {
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

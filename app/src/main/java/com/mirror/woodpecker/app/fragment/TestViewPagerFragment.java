package com.mirror.woodpecker.app.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/3/1.
 */
public class TestViewPagerFragment extends BaseFragment {
    private String mTypeId;
    private TextView mTv;

    @Override
    public int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTypeId = getArguments().getString(INTENT_ID);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTv = (TextView)view.findViewById(R.id.tv);
        mTv.setText(mTypeId);

    }
}

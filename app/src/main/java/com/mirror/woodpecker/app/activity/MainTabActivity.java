package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.fragment.AutoScrollPagerFragment;
import com.mirror.woodpecker.app.fragment.IndexFragment;
import com.mirror.woodpecker.app.fragment.TestRecyclerViewFragment;
import com.mirror.woodpecker.app.fragment.TestSpinnersRecyclerViewFragment;

/**
 * Created by dongqian on 16/1/3.
 */
public class MainTabActivity extends BaseTabActivity {
    @Override
    public String[] setTabTitles() {
        return new String[]{"首页","客服调度","维修维保","个人中心"};
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
        return new Class[]{IndexFragment.class,TestSpinnersRecyclerViewFragment.class,
                TestRecyclerViewFragment.class,AutoScrollPagerFragment.class};
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }
}

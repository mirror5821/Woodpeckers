package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.fragment.AutoScrollPagerFragment;
import com.mirror.woodpecker.app.fragment.IndexFragment;
import com.mirror.woodpecker.app.fragment.TestListFragment;
import com.mirror.woodpecker.app.fragment.TestRecyclerViewFragment;

import dev.mirror.library.android.activity.DevBaseTabActivity;

/**
 * Created by dongqian on 16/1/3.
 */
public class MainTabActivity extends DevBaseTabActivity {
    @Override
    public String[] setTabTitles() {
        return new String[]{"测试","测试2","测试3","测试4"};
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab1, R.drawable.tab1, R.drawable.tab1};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
        return new Class[]{IndexFragment.class,TestListFragment.class,
                TestRecyclerViewFragment.class,AutoScrollPagerFragment.class};
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }
}

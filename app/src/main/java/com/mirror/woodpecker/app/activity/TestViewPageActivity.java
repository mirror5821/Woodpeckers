package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.mirror.woodpecker.app.fragment.TestViewPagerFragment;

/**
 * Created by 王沛栋 on 2016/3/1.
 */
public class TestViewPageActivity extends BaseViewPagerActivity {
    private String [] mTypes = {"测试1","测试2"};

    @Override
    public String[] setTypes() {
        return mTypes;
    }

    @Override
    public void onPageChangeListenr(int position) {
        selectTab(position);
    }

    @Override
    public void onCheckChangeListener(RadioGroup group, int checkedId) {
        mViewPager.setCurrentItem(checkedId);
    }

    @Override
    public Fragment setViewPagerFragment(int position) {

        TestViewPagerFragment commentFragment = new TestViewPagerFragment();
        Bundle b = new Bundle();
        b.putString(INTENT_ID, mTypes[position]);

        commentFragment.setArguments(b);
        return commentFragment;
    }

    private void selectTab(int tab){
        Rbs[tab].setChecked(true);
    }
}

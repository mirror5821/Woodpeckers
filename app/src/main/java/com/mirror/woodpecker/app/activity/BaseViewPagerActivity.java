package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mirror.woodpecker.app.R;

import dev.mirror.library.android.util.ScreenUtil;

/**
 * Created by 王沛栋 on 2016/3/1.
 */
public abstract class BaseViewPagerActivity extends BaseActivity {
    public RadioGroup mRG;
    public ViewPager mViewPager;
    public RadioButton[] Rbs;

    public String [] Types;
    private int mViewPagerCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_viewpager);

        mRG = (RadioGroup)findViewById(R.id.view_select);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        Types = setTypes();
        int typeLength = 0;
        if(Types != null){
            typeLength = Types.length;

            Rbs = new RadioButton[typeLength];

            int rbWidth = (ScreenUtil.getScreenWidth(BaseViewPagerActivity.this))/typeLength;
            for(int i=0; i<typeLength; i++){
                View v = getLayoutInflater().inflate(R.layout.view_tab_rb, null);

                RadioButton tempButton =(RadioButton)v.findViewById(R.id.rb1);
                tempButton.setText(Types[i]);
                tempButton.setId(i);
                Rbs[i] = tempButton;


                mRG.addView(tempButton, rbWidth, LinearLayout.LayoutParams.MATCH_PARENT);
            }

            mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    onCheckChangeListener(group, checkedId);
                }
            });

            Rbs[0].setChecked(true);

        }else{
            mRG.setVisibility(View.GONE);
            typeLength = 1;
        }

        mViewPagerCount = typeLength;
        mViewPager.setOffscreenPageLimit(mViewPagerCount);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                onPageChangeListenr(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });


        initView();
    }

    /**
     * 可以创建一些新的视图
     * 或者修改原来的视图
     */
    public void initView(){

    }
    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return setViewPagerFragment(arg0);
        }

        @Override
        public int getCount() {
            return mViewPagerCount;
        }

    }


    public abstract String[] setTypes();
    public abstract void onPageChangeListenr(int position);
    public abstract void onCheckChangeListener(RadioGroup group, int checkedId);
    public abstract Fragment setViewPagerFragment(int position);

}

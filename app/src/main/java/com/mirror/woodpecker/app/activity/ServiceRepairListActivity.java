package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.fragment.ServiceRepairRecyclerViewFragment;

/**
 * Created by 王沛栋 on 2016/3/1.
 * 用户维修单
 */
public class ServiceRepairListActivity extends BaseViewPagerActivity {
    private String [] mTypes;

    @Override
    public String[] setTypes() {
        //数据1代表单位主管，2代表部门主管，3代表客服，4代表维修人员，0为普通用户
        switch (AppContext.USER_ROLE_ID){
            case 1:
                mTypes = new String[]{"我的维修单", "部门维修单", "单位维修单"};
                break;
            case 2:
                mTypes = new String[]{"我的维修单","部门维修单"};
                break;
            default:
                mTypes = null;
                break;
        }
        return mTypes;
    }

    @Override
    public void initView() {
        super.initView();
        setBack();
        setTitleText("维修单列表");
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

        ServiceRepairRecyclerViewFragment commentFragment = new ServiceRepairRecyclerViewFragment();
        Bundle b = new Bundle();
        b.putInt(INTENT_ID,position);

        commentFragment.setArguments(b);
        return commentFragment;
    }

    private void selectTab(int tab){
        Rbs[tab].setChecked(true);
    }
}

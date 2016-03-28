package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TabHost;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.fragment.AutoScrollPagerFragment;
import com.mirror.woodpecker.app.fragment.IndexFragment;
import com.mirror.woodpecker.app.fragment.MyFragment;
import com.mirror.woodpecker.app.fragment.RepairRepairRecyclerViewFragment;
import com.mirror.woodpecker.app.fragment.ServiceRepairRecyclerViewFragment;
import com.mirror.woodpecker.app.iface.OnTabSelect;

/**
 * Created by dongqian on 16/1/3.
 */
public class MainTabActivity extends BaseTabActivity implements OnTabSelect{
    private int mCode;//请求参数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //得判断第三选项卡中是否已经登陆
		mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(mTabs[1])) {
                    mCode = 1;
                    if (AppContext.USER_ROLE_ID != 3) {
                        showToast("您无权限查看此处内容");
//                        startActivityForResult(new Intent(MainTabActivity.this, Login2Activity.class), 7801);
                        onSelect(0);
                    }
                } else if (tabId.equals(mTabs[2])) {
                    mCode = 2;
                    if (AppContext.USER_ROLE_ID != 4) {
                        showToast("您无权限查看此处内容");
//                        startActivityForResult(new Intent(MainTabActivity.this, Login2Activity.class), 7802);
                        onSelect(0);
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==Activity.RESULT_OK){
            if(mCode == 1){
                if(AppContext.USER_ROLE_ID != 3){
                   showToast("您无权限查看此处内容");
                }
            }
            if(mCode == 2){
                if(AppContext.USER_ROLE_ID != 4){
                    showToast("您无权限查看此处内容");
                }
            }
            /*if(AppContext.USER_ROLE_ID == 3){
                onSelect(1);
            }else if(AppContext.USER_ROLE_ID == 4){
                onSelect(2);
            }else{
                onSelect(0);
            }*/
        }
    }



    private String [] mTabs = {"首页","客服调度","维修维保","个人中心"};
    @Override
    public String[] setTabTitles() {
        return mTabs;
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
        return new Class[]{IndexFragment.class,ServiceRepairRecyclerViewFragment.class,
                RepairRepairRecyclerViewFragment.class,MyFragment.class};
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }

    @Override
    public void onSelect(int index) {
        mFragmentTabHost.setCurrentTab(index);
    }
}

package com.mirror.woodpecker.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Kefu;
import com.mirror.woodpecker.app.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirror on 16/1/3.
 */
public class RepairRepairFragment extends BaseFragment {
    private String [] orderStatus = {"未处理","已完成", "已查看", "等待接单", "已接单", "解决中", "等待调货",
            "确定调货，货已到", "已解决"};

    @Override
    public int setLayoutId() {
        return R.layout.fragment_service_repair;
    }

    private boolean isLoad = false;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(AppContext.USER_ROLE_ID != 4){
           return;
        }

        if(!isLoad){
            isLoad = true;
            // 维修人员点击进入维修维保时，默认不要显示成未处理的状态的订单列表，
            // 直接默认成等待接单状态的订单列表。因为维修人员看不到未处理的，
            // 所以一打开页面就会显示“暂无维修单”或者空白，不合理
            setTitleText("等待接单维修单列表");

            RepairRepairRecyclerViewRefreshFragment fragment = new RepairRepairRecyclerViewRefreshFragment();
            Bundle b = new Bundle();
            b.putInt(INTENT_ID,3);
            fragment.setArguments(b);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_list, fragment).commit();
        }
    }



    private List<Kefu> mLists;

    private List<Kefu> mSelector(){
        if(mLists == null){
            mLists = new ArrayList<>();
            for(int i=0;i<orderStatus.length;i++){
                Kefu k = new Kefu();
                k.setId(i);
                k.setUsername(orderStatus[i]+"维修单列表");

                mLists.add(k);
            }

        }
        return mLists;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bar_title:
                UIHelper uiHelper = new UIHelper();
                uiHelper.initSelectUnitView(getActivity(),mSelector(), new DialogInterface() {
                    @Override
                    public void getPosition(int position) {
                        Kefu u = mLists.get(position);
                        RepairRepairRecyclerViewRefreshFragment fragment = new RepairRepairRecyclerViewRefreshFragment();
                        Bundle b = new Bundle();
                        b.putInt(INTENT_ID,u.getId());
                        fragment.setArguments(b);

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_ENTER_MASK);
                        fragmentTransaction.replace(R.id.fragment_list, fragment).commit();

                        mTvTitleBar.setText(u.getUsername());
                    }
                });
                break;
        }
    }
}

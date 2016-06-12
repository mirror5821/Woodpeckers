package com.mirror.woodpecker.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Kefu;
import com.mirror.woodpecker.app.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mirror on 16/1/3.
 */
public class ServiceRepairFragment extends BaseFragment {
    private String [] orderStatus = {"未处理","客服关闭", "已查看", "等待接单", "已接单", "解决中", "等待调货状态",
            "确定调货，货已到", "已解决", "最终关闭"};

    @Override
    public int setLayoutId() {
        return R.layout.fragment_service_repair;
    }

    private boolean isLoad = false;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isLoad){
            isLoad = true;
            setTitleText("全部维修单列表");

            ServiceRepairRecyclerViewRefreshFragment fragment = new ServiceRepairRecyclerViewRefreshFragment();
            Bundle b = new Bundle();
            b.putInt(INTENT_ID,-1);
            fragment.setArguments(b);

            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment).commit();
        }
    }



    private List<Kefu> mLists;

    private List<Kefu> mSelector(){
        if(mLists == null){
            mLists = new ArrayList<>();

            Kefu kefu = new Kefu();
            kefu.setId(-1);
            kefu.setUsername("全部维修单列表");

            mLists.add(kefu);
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
                        ServiceRepairRecyclerViewRefreshFragment fragment = new ServiceRepairRecyclerViewRefreshFragment();
                        Bundle b = new Bundle();
                        b.putInt(INTENT_ID,u.getId());
                        fragment.setArguments(b);

                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setTransition(fragmentTransaction.TRANSIT_ENTER_MASK);
                        fragmentTransaction.replace(R.id.content, fragment).commit();

                        mTvTitleBar.setText(u.getUsername());
                    }
                });
                break;
        }
    }
}

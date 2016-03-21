package com.mirror.woodpecker.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.LoginActivity;
import com.mirror.woodpecker.app.activity.RepairAddActivity;
import com.mirror.woodpecker.app.activity.UpdatePassActivity;
import com.mirror.woodpecker.app.activity.UserRepairListActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class MyFragment extends BaseFragment {
    private TextView mTvUpdatePass;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvUpdatePass = (TextView)view.findViewById(R.id.tv_update);
        mTvUpdatePass.setOnClickListener(this);

        mHttpClient.postData1(CONSTANS, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_update:
                if(AppContext.USER_ROLE_ID==-1){
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class),LOGIN_CODE1);
                }else{
                    startActivity(new Intent(getActivity(), UpdatePassActivity.class));
                }
                break;
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case LOGIN_CODE1:
                    startActivity(new Intent(getActivity(), UpdatePassActivity.class));
                    *//*if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                        startActivity(new Intent(getActivity(), RepairAddActivity.class));
                    }
                    break;
                case LOGIN_CODE2:
                    if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                        startActivity(new Intent(getActivity(), UserRepairListActivity.class));
                    }*//*
                    break;

            }
        }
    }*/
}

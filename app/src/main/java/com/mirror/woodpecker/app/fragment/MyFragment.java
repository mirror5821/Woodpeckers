package com.mirror.woodpecker.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.ContactUsActivity;
import com.mirror.woodpecker.app.activity.LoginActivity;
import com.mirror.woodpecker.app.activity.RegistersActivity;
import com.mirror.woodpecker.app.activity.RepairAddActivity;
import com.mirror.woodpecker.app.activity.UpdatePassActivity;
import com.mirror.woodpecker.app.activity.UserInfoActivity;
import com.mirror.woodpecker.app.activity.UserRepairListActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class MyFragment extends BaseFragment {
    private TextView mTvUpdatePass;
    private LinearLayout mViewLogin;
    private LinearLayout mViewRepair;
    private TextView mTvName;
    private TextView mTvInfo;
    private TextView mTvAbout;

    private Button mBtnLogin,mBtnRegister;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTvUpdatePass = (TextView)view.findViewById(R.id.tv_update);
        mTvName = (TextView)view.findViewById(R.id.name);
        mTvInfo = (TextView)view.findViewById(R.id.tv_info);
        mTvAbout = (TextView)view.findViewById(R.id.tv_about);
        mViewLogin = (LinearLayout)view.findViewById(R.id.view_login);
        mViewRepair = (LinearLayout)view.findViewById(R.id.view_repair);

        mTvUpdatePass.setOnClickListener(this);
        mTvInfo.setOnClickListener(this);
        mTvAbout.setOnClickListener(this);

        mBtnLogin = (Button)view.findViewById(R.id.btn_login);
        mBtnRegister = (Button)view.findViewById(R.id.btn_register);

        if(AppContext.USER_ROLE_ID !=-1){
            mTvName.setText(SharePreferencesUtil.getUserInfo(getActivity()).getUsername());
            mTvName.setVisibility(View.VISIBLE);
            mViewLogin.setVisibility(View.GONE);

            mBtnLogin.setOnClickListener(this);
            mBtnRegister.setOnClickListener(this);

            if(AppContext.USER_ROLE_ID !=3 || AppContext.USER_ROLE_ID !=4){
                mViewRepair.setVisibility(View.GONE);
            }
        }else{
            mTvName.setVisibility(View.GONE);
            mViewLogin.setVisibility(View.VISIBLE);
        }

        mViewLogin.setOnClickListener(this);


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
            case R.id.btn_login:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class),LOGIN_CODE2);
                break;
            case R.id.btn_register:
                startActivity(new Intent(getActivity(), RegistersActivity.class));
                break;
            case R.id.tv_info:
                if(AppContext.USER_ROLE_ID==-1){
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class),LOGIN_CODE3);
                }else{
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                }
                break;
            case R.id.tv_about:
                startActivity(new Intent(getActivity(), ContactUsActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case LOGIN_CODE1:
                    startActivity(new Intent(getActivity(), UpdatePassActivity.class));
                    break;
                case LOGIN_CODE2:
                    break;
                case LOGIN_CODE3:
                    startActivity(new Intent(getActivity(), UserInfoActivity.class));
                    break;

            }
        }
    }
}

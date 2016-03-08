package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Units;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.UIHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class RegistersActivity extends BaseActivity {
    private RadioGroup mRg;
    private RadioButton mRb1,mRb2;
    private TextView mTvSignUnit;

    private List<Units> mLists = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsters);
        setBack();
        setTitleText("登录");

        mTvSignUnit = (TextView)findViewById(R.id.sign_unit);
        mTvSignUnit.setOnClickListener(this);

        mRg = (RadioGroup)findViewById(R.id.rg);
        mRb1 = (RadioButton)findViewById(R.id.rb1);
        mRb2 = (RadioButton)findViewById(R.id.rb2);

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb2) {
                    mTvSignUnit.setVisibility(View.VISIBLE);
                } else {
                    mTvSignUnit.setVisibility(View.GONE);
                }
            }
        });
        mRb1.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sign_unit:
                loadUnit();
                break;
        }
    }

    private void initSelectUnitView(){
        UIHelper uiHelper = new UIHelper();
        uiHelper.initSelectUnitView(RegistersActivity.this,mLists, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                mTvSignUnit.setText(mLists.get(position).getName());
            }
        });

        cancelProgressDialog();
    }


    private void loadUnit(){
        showProgressDialog("正在加载数据");
        if(mLists.size()>0){
            initSelectUnitView();
        }else{
            mHttpClient.postData(REPAIR_COM, null, new AppAjaxCallback.onRecevierDataListener<Units>() {

                @Override
                public void onReceiverData(List<Units> data, String msg) {
                    mLists.addAll(data);
                    initSelectUnitView();
                }

                @Override
                public void onReceiverError(String msg) {
                    showToast(msg);
                    cancelProgressDialog();
                }

                @Override
                public Class<Units> dataTypeClass() {
                    return Units.class;
                }
            });
        }

    }
}

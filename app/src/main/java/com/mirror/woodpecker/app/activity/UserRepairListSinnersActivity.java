package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.fragment.UserRepairRecyclerViewForSpinnerFragment;
import com.mirror.woodpecker.app.model.Spinners;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王沛栋 on 2016-06-13.
 */
public class UserRepairListSinnersActivity extends BaseSpinners2Activity{

    private String [] mUserRoles = {"我的维修单", "部门维修单", "单位维修单"};
    private String [] mTypes = {"未处理", "已完成"};

    private int mType1 = 0;
    private int mType2 = 0;
    @Override
    public void initView() {
        setBack();
        setTitleText("我的维修单");
        loadView();
    }

    private void loadView(){
        UserRepairRecyclerViewForSpinnerFragment fragment = new UserRepairRecyclerViewForSpinnerFragment();
        Bundle b = new Bundle();
        b.putInt(INTENT_ID,mType1);
        b.putInt("TYPE2",mType2);
        fragment.setArguments(b);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_content, fragment).commit();
    }
    @Override
    public List setSpinnerData1() {
        List<Spinners> data = new ArrayList<>();
        switch (AppContext.USER_ROLE_ID){
            case 1:
                for (int i =0;i<3;i++){
                    Spinners s1 = new Spinners();
                    s1.setId(i);
                    s1.setName(mUserRoles[i]);

                    data.add(s1);
                }
                break;
            case 2:
                for (int i =0;i<2;i++){
                    Spinners s1 = new Spinners();
                    s1.setId(i);
                    s1.setName(mUserRoles[i]);

                    data.add(s1);
                }
                break;
            default:
                Spinners s1 = new Spinners();
                s1.setId(0);
                s1.setName(mUserRoles[0]);
                data.add(s1);

                break;
        }
        return data;
    }

    @Override
    public List setSpinnerData2() {
        //规定0为未处理完成的 1为处理完成的
        List<Spinners> data = new ArrayList<>();
        for (int i =0;i<mTypes.length;i++){
            Spinners s1 = new Spinners();
            s1.setId(i);
            s1.setName(mTypes[i]);

            data.add(s1);
        }
        return data;
    }


    @Override
    public void Spinner2OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
        mType2 = arg2;
        loadView();
    }

    @Override
    public void Spinner1OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
        mType1 = arg2;
        loadView();
    }
}
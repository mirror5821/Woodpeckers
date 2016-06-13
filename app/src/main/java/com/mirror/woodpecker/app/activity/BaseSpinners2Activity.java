package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.AddrAdapter;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/4.
 * 这个是基础的spinner封装
 * 如果下面内容非列表形式，则将listview更换成普通的view即可
 */
public abstract class BaseSpinners2Activity<T> extends BaseActivity {
    public Spinner spinner1,spinner2;
    public List<T> Types1;
    public List<T> Types2;
    public AddrAdapter mAdapter1;
    public AddrAdapter mAdapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_spinners2);
        spinner1 = (Spinner)findViewById(R.id.spinner1);
        spinner2 = (Spinner)findViewById(R.id.spinner2);

        bindSpinner();

    }



    private void bindSpinner(){
        Types1 = setSpinnerData1();
        Types2 = setSpinnerData2();

        mAdapter1 = new AddrAdapter(this,Types1,3);
        mAdapter2 = new AddrAdapter(this,Types2,2);

        spinner1.setAdapter(mAdapter1);
        spinner1.setVisibility(View.VISIBLE);//设置默认显示
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spinner1OnItemClick(arg0, arg1, arg2, arg3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinner2.setAdapter(mAdapter2);
        spinner2.setVisibility(View.VISIBLE);//设置默认显示
        spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spinner2OnItemClick(arg0,arg1,arg2,arg3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        initView();
    }

    public abstract void initView();
    public abstract List<T> setSpinnerData1();
    public abstract List<T> setSpinnerData2();

    public abstract void Spinner1OnItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3);
    public abstract void Spinner2OnItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3);
}

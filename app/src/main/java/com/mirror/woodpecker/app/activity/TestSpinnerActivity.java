package com.mirror.woodpecker.app.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mirror.woodpecker.app.model.TestSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/4.
 */
public class TestSpinnerActivity extends BaseSpinnersActivity {
    @Override
    public List setSpinnerData1() {
        List<TestSpinner> data = new ArrayList<>();
        for(int i=0;i<5;i++){
            TestSpinner t = new TestSpinner();
            t.setId(i);
            t.setName("测试" + i);
            data.add(t);
        }
        return data;
    }

    @Override
    public List setSpinnerData2() {
        List<TestSpinner> data = new ArrayList<>();
        for(int i=0;i<5;i++){
            TestSpinner t = new TestSpinner();
            t.setId(i);
            t.setName("呵呵" + i);
            data.add(t);
        }
        return data;
    }

    @Override
    public void Spinner2OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
        showToast("你的选择是：" + ((TextView) arg1).getText());
    }

    @Override
    public void Spinner1OnItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
        showToast("你的选择是：" + ((TextView) arg1).getText());
    }
}

package com.mirror.woodpecker.app.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.AddrAdapter;
import com.mirror.woodpecker.app.iface.DialogInterface;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public class UIHelper<T> {

    public void initSelectUnitView(Context context,List<T> list, final DialogInterface dInterface){
        View view = LayoutInflater.from(context).inflate(R.layout.base_listview, null);


        ListView listView = (ListView)view.findViewById(android.R.id.list);//this为获取当前的上下文
        listView.setFadingEdgeLength(0);

        AddrAdapter mAdapter = new AddrAdapter(context,list,1);
        listView.setAdapter(mAdapter);


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("请选择").setView(view)//在这里把写好的这个listview的布局加载dialog中
                .create();
        dialog.setCanceledOnTouchOutside(false);//使除了dialog以外的地方不能被点击
        dialog.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//响应listview中的item的点击事件

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                dInterface.getPosition(arg2);
                dialog.cancel();
            }
        });
    }
}

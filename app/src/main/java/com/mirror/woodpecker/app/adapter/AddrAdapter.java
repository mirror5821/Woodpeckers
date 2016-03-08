package com.mirror.woodpecker.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.AddrBase;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public  class AddrAdapter<T extends AddrBase> extends BaseAdapter {
    private int mType;
    private Context mContext;
    private List<T> mList;
    public AddrAdapter(Context context,List<T> list,int type){
        this.mType = type;
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            if (mType == 2) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_d, null);
            }else{
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_l, null);
            }

        }

        TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
        tv.setText(mList.get(position).getAddrName());
        return convertView;
    }
}
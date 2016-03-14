package com.mirror.woodpecker.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mirror.woodpecker.app.model.AddrBase;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public  class NormalAdapter<T extends AddrBase> extends BaseAdapter {
    private int mType;
    private Context mContext;
    private List<T> mList;
    private int mLayoutId;
    private LayoutInflater mInflater;
    public NormalAdapter(Context context, List<T> list,int layoutId){
        this.mContext = context;
        this.mList = list;
        this.mLayoutId = layoutId;
        mInflater = LayoutInflater.from(mContext);
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
            convertView =mInflater.inflate(mLayoutId,null);
        }

        TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
        tv.setText(mList.get(position).getAddrName());
        return convertView;
    }
}
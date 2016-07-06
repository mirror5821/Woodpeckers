package com.mirror.woodpecker.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.About;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public  class ItemWithImgAdapter extends BaseAdapter {
    private int mType;
    private Context mContext;
    private List<About> mList;
    private int mLayoutId;
    private LayoutInflater mInflater;
    public ItemWithImgAdapter(Context context, List<About> list, int layoutId){
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
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        if (!TextUtils.isEmpty(mList.get(position).getIcon()))
            AppContext.displayImage(img,"http://zmnyw.cn"+mList.get(position).getIcon());
        TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
        tv.setText(mList.get(position).getAddrName());
        return convertView;
    }
}
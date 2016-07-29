package com.mirror.woodpecker.app.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Pay;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public  class PayListImageAdapter extends BaseAdapter {
    private int mType;
    private Context mContext;
    private List<Pay.Paths> mList;
    private LayoutInflater mInflater;
    public PayListImageAdapter(Context context, List<Pay.Paths> list){
        this.mContext = context;
        this.mList = list;
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
            convertView =mInflater.inflate(R.layout.item_pay_img,null);
        }
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        if (!TextUtils.isEmpty(mList.get(position).getPath())) {
            AppContext.displayImage(img, "http://zmnyw.cn" + mList.get(position).getPath());
            System.out.println("--------------"+"http://zmnyw.cn"+mList.get(position).getPath());
        }

        return convertView;
    }
}
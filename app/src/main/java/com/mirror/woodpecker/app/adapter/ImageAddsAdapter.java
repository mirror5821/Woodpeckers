package com.mirror.woodpecker.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.mirror.woodpecker.app.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/16.
 */
public class ImageAddsAdapter extends BaseAdapter {
    private List<String> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    public ImageAddsAdapter(Context context, List<String> mList){
        this.mList = mList;
        mInflater = LayoutInflater.from(context);
        mContext = context;
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

    private ImageOptions mImageOptions;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_img_add, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.img_add);
            viewHolder.imgDelete = (ImageView)convertView.findViewById(R.id.img_delete);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(mImageOptions == null){
            mImageOptions = new ImageOptions.Builder()
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(true)
                            // 加载中或错误图片的ScaleType
                            //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    .setLoadingDrawableId(R.mipmap.ic_default_error).setAutoRotate(true)
                    .setFailureDrawableId(R.mipmap.ic_default_error)
                    .build();
        }
        String imgUrl = mList.get(position);

        if(mList.size()==1){
            viewHolder.imgDelete.setVisibility(View.GONE);
            viewHolder.img.setImageResource(R.mipmap.ic_img_add);
        }else{
            if (imgUrl!=null){
                File imageFile = new File(imgUrl);
                x.image().bind(viewHolder.img, imageFile.toURI().toString(), mImageOptions);
                viewHolder.imgDelete.setVisibility(View.VISIBLE);
            }else{
                viewHolder.imgDelete.setVisibility(View.GONE);
                viewHolder.img.setImageResource(R.mipmap.ic_img_add);
            }
        }

        return convertView;
    }

    private static class ViewHolder{
        ImageView img,imgDelete;
    }
}

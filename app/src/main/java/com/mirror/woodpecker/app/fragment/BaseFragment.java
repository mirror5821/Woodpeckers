package com.mirror.woodpecker.app.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.util.AppHttpClient;

import dev.mirror.library.android.fragment.DevBaseFragment;

/**
 * Created by dongqian on 16/1/3.
 */
public class BaseFragment extends DevBaseFragment implements Constants{
    private ImageView mImgBack;
    private TextView mTvTitleRight;
    private TextView mTvTitleBar;

    public AppHttpClient mHttpClient = new AppHttpClient();

    /**
     * 设置后退事件
     */
    public void setBack(){
        mImgBack = (ImageView)getView().findViewById(R.id.left_icon);
        mImgBack.setImageResource(R.mipmap.ic_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    /**
     * 设置titlebar文字
     * @param title
     */
    public void setTitleText(String title){
        mTvTitleBar = (TextView)getView().findViewById(R.id.bar_title);
        mTvTitleBar.setText(title);
        mTvTitleBar.setOnClickListener(this);
    }

    /**
     * 设置titlebar右侧文字和点击事件
     * @param str
     */

    public void setRightTitle(String str){
        mTvTitleRight = (TextView)getView().findViewById(R.id.right_text);
        mTvTitleRight.setText(str);
    }
}

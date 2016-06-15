package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.util.AppHttpClient;

import cn.jpush.android.api.JPushInterface;
import dev.mirror.library.android.activity.DevBaseActivity;

/**
 * Created by mirror on 16/1/3.
 */
public class BaseActivity extends DevBaseActivity implements Constants{
    private ImageView mImgBack;
    private TextView mTvTitleRight;
    public TextView mTvTitleBar;

    public AppHttpClient mHttpClient = new AppHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//        BaseActivityManager b = BaseActivityManager.getInstance();
//        b.addActivity(this);
        //如果没有巡检
        if(!AppContext.IS_XUNJINNING){
            new AppContext().loadXunjian();
        }

        JPushInterface.init(getApplicationContext());
    }

    /**
     * 设置后退事件
     */
    public void setBack(){
        mImgBack = (ImageView)findViewById(R.id.left_icon);
        mImgBack.setImageResource(R.mipmap.ic_back);
        mImgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置titlebar文字
     * @param title
     */
    public void setTitleText(String title){
        mTvTitleBar = (TextView)findViewById(R.id.bar_title);
        mTvTitleBar.setText(title);
        mTvTitleBar.setOnClickListener(this);
    }

    /**
     * 设置titlebar右侧文字和点击事件
     * @param str
     */

    public void setRightTitle(String str){
        mTvTitleRight = (TextView)findViewById(R.id.right_text);
        mTvTitleRight.setText(str);
        mTvTitleRight.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(getApplicationContext());

    }

    @Override
    public void onPause() {
        super.onPause();
        JPushInterface.onPause(getApplicationContext());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}

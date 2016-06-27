package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.util.AppHttpClient;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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

        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, "13938423219"));
    }


    /**
     * 以下内容为设置推送消息
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;

                case 6002:
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;

            }

        }

    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code) {
                case 0:
                    break;

                case 6002:
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    break;

            }

        }

    };

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private final Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;

                case MSG_SET_TAGS:
                    JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                    break;
            }
        }
    };


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

package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.model.User;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import dev.mirror.library.android.util.DateUtil;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/22.
 */
public class UserInfoActivity extends BaseActivity {
    private int mUserId;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mUserId = AppContext.USER_ID;
        loadData();
    }
    private void loadData(){
        JSONObject jb = new JSONObject();
        try {
            jb.put("uid",mUserId);
        }catch (JSONException e){

        }
        mHttpClient.postData1(USER_INFOMATION, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mUser = JsonUtils.parse(data,User.class);
                initView();
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvPhone;
    private TextView mTvRTime;
    private TextView mTvComName;
    private TextView mTvProjectName;
    private TextView mTvJobType;
    private TextView mTvSubCount;
    private TextView mTvSubFinishCount;
    private TextView mTvSubingCount;

    private void initView(){
        mTvName = (TextView)findViewById(R.id.name);
        mTvEmail = (TextView)findViewById(R.id.email);
        mTvPhone = (TextView)findViewById(R.id.phone);
        mTvRTime = (TextView)findViewById(R.id.r_time);
        mTvComName = (TextView)findViewById(R.id.com_name);
        mTvProjectName = (TextView)findViewById(R.id.p_name);
        mTvJobType = (TextView)findViewById(R.id.job_type);
        mTvSubCount = (TextView)findViewById(R.id.sub_count);
        mTvSubFinishCount = (TextView)findViewById(R.id.sub_finish);
        mTvSubingCount = (TextView)findViewById(R.id.sub_ing);

        mTvName.setText(TextUtils.isEmpty(mUser.getUsername())?"暂无":mUser.getUsername());
        mTvEmail.setText(TextUtils.isEmpty(mUser.getEmail())?"暂无":mUser.getEmail());
        mTvPhone.setText(TextUtils.isEmpty(mUser.getMobile())?"暂无":mUser.getMobile());
        mTvRTime.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm",mUser.getReg_time()));
        mTvComName.setText(TextUtils.isEmpty(mUser.getCompanyname())?"暂无":mUser.getCompanyname());
        mTvProjectName.setText(TextUtils.isEmpty(mUser.getProjectname())?"暂无":mUser.getProjectname());
        mTvJobType.setText(TextUtils.isEmpty(mUser.getJobname())?"暂无":mUser.getJobname());
        mTvSubCount.setText(mUser.getCount()+"单");
        mTvSubFinishCount.setText(mUser.getDonecount()+"单");
        mTvSubingCount.setText(mUser.getOncount()+"单");


    }
}

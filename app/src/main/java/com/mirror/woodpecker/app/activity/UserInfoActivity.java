package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.User;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.DateUtil;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/22.
 */
public class UserInfoActivity extends BaseActivity {
    private int mUserId;
    private User mUser;

    private Button mBtn;
    private ImageView mImgHeader;

    private ImageTools mImageTools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        setBack();
//        setTitleText("用户信息");
        mUserId = AppContext.USER_ID;

        mImageTools = new ImageTools(this);

        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

        mImgHeader = (ImageView)findViewById(R.id.img_header);
        mList = new ArrayList<>();

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

        if(!TextUtils.isEmpty(mUser.getHeadimg())){
            AppContext.displayHeaderImage(mImgHeader,mUser.getHeadimg());
        }

        mImgHeader.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                AppContext.USER_ROLE_ID = -1;
                AppContext.USER_ID = -1;
                AppContext.IS_LOGIN = false;
                SharePreferencesUtil.deleteInfo(getApplicationContext());
                finish();
                break;
            case R.id.img_header:
                openImage();
                break;
        }
    }

    private List<String> mList;

    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 2;
    private void openImage(){
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;;
//        selectedMode = MultiImageSelectorActivity.MODE_SINGLE;


        int maxNum = 1;
        Intent intent = new Intent(UserInfoActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
//        if (mSelectPath != null && mSelectPath.size() > 0) {
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//        }
        if (mList != null && mList.size() > 1) {
            mList.remove(mList.size()-1);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList)mList);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE){
            if(resultCode == RESULT_OK){
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String imgUrl = mSelectPath.get(0);

                AppContext.displayLocHeaderImage(mImgHeader,imgUrl);

                uploadHeader(imgUrl);
            }
        }
    }

    private void uploadHeader(String imageUrl){
//        37.用户信息修改接口（uinfoedit）
//        用户ID	uid
//        头像	headimg
//        真实姓名	truename
        JSONObject jb = new JSONObject();
        try{
            jb.put("uid",mUserId);
            jb.put("headimg",mImageTools.filePathToString(imageUrl));
            jb.put("truename","");
        }catch (JSONException e){

        }
        mHttpClient.postData1(USER_INFO_UPDATE, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(data);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}

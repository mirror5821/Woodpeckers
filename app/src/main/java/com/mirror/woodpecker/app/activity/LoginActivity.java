package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.User;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class LoginActivity extends BaseActivity {
    private EditText mEtName,mEtPass;
    private Button mBtnRegister,mBtnLogin;
    private TextView mTvForget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setBack();
        setTitleText("登录");

        mEtName = (EditText)findViewById(R.id.name);
        mEtPass = (EditText)findViewById(R.id.pass);

        if(SharePreferencesUtil.getLoginInfo(getApplicationContext())!=null){
            login();
        }
//        mEtName.setText("kefu");
//        mEtPass.setText("asd123456");

        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnRegister = (Button)findViewById(R.id.btn);
        mTvForget = (TextView)findViewById(R.id.tv_forget);

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
                break;
            case R.id.btn:
                startActivity(new Intent(LoginActivity.this,RegistersActivity.class));
                break;
        }
    }


    public void login(){
        final String name;
        final String pass;
        if(SharePreferencesUtil.getLoginInfo(getApplicationContext())!=null){
            name = SharePreferencesUtil.getLoginInfo(getApplicationContext()).getUsername();
            pass = SharePreferencesUtil.getLoginInfo(getApplicationContext()).getEmail();
        }else{
            name = mEtName.getText().toString().trim();
            pass = mEtPass.getText().toString().trim();
        }


        if(TextUtils.isEmpty(name)){
            showToast("请输入注册邮箱或手机号码");
            return;
        }

        if(TextUtils.isEmpty(pass)){
            showToast("请输入密码");
            return;
        }


        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("username", name);
            jb.put("password", pass);


        }catch (JSONException e){

        }

        System.out.println("----------"+jb.toString());
        ap.postData1(LOGIN_TEST, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, name));
                /**
                 * 返回的数据中role_id为角色ID，数据1代表单位主管，
                 * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
                 */
                SharePreferencesUtil.saveLoginInfo(getApplicationContext(), name, pass);
                SharePreferencesUtil.saveUserInfo(getApplicationContext(), data);
                User user= SharePreferencesUtil.getUserInfo(getApplicationContext());
                AppContext.USER_ROLE_ID = user.getRole_id();

                SharePreferencesUtil.saveUserRoleId(getApplicationContext(),AppContext.USER_ROLE_ID);
                AppContext.USER_ID = user.getId();
                AppContext.IS_LOGIN = true;
                Uri datas = Uri.parse(data);
                Intent intent = new Intent(null,datas);
                setResult(RESULT_OK, intent);
                finish();
//                startActivity(new Intent(LoginActivity.this, UserRepairListActivity.class));
//                finish();
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
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
}

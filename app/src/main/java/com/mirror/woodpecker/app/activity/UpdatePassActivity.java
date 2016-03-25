package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.User;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class UpdatePassActivity extends BaseActivity {
    private EditText mEtName,mEtPass,mEtPass2,mEtPassOld;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pass);
        setBack();
        setTitleText("修改密码");

        mEtName = (EditText)findViewById(R.id.name);
        mEtPass = (EditText)findViewById(R.id.pass);
        mEtPass2 = (EditText)findViewById(R.id.pass2);
        mEtPassOld = (EditText)findViewById(R.id.pass_old);

        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                sub();
                break;
        }
    }

    private void sub(){

        final String pass = mEtPass.getText().toString().trim();
        final String pass2 = mEtPass2.getText().toString().trim();
        final String passOld = mEtPassOld.getText().toString().trim();

        /*if(TextUtils.isEmpty(name)){
            showToast("请输入注册邮箱或手机号码");
            return;
        }*/

        if(TextUtils.isEmpty(pass)){
            showToast("请输入新的密码");
            return;
        }

        if(TextUtils.isEmpty(pass2)){
            showToast("请输入重复密码");
            return;
        }

        if(TextUtils.isEmpty(passOld)){
            showToast("请输入原始密码");
            return;
        }

        /* 5.修改密码接口(password)

        原始密码	old
        新密码	password
        确认密码	repassword
        登录用户ID	uid*/

        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("old", passOld);
            jb.put("password", pass);
            jb.put("repassword", pass2);
            jb.put("uid", AppContext.USER_ID);

        }catch (JSONException e){

        }

        System.out.println("----------"+jb.toString());
        ap.postData1(UPDATE_PASS, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                /**
                 * 返回的数据中role_id为角色ID，数据1代表单位主管，
                 * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
                 */
                User user= SharePreferencesUtil.getLoginInfo(getApplicationContext());
                System.out.println("---------------------------u+"+user.getUsername());
                SharePreferencesUtil.saveLoginInfo(getApplicationContext(), user.getUsername(), pass);
//                SharePreferencesUtil.saveUserInfo(getApplicationContext(), data);
                showToast(msg);

                finish();
            }

            @Override
            public void onError(String msg) {
                showToast("操作失败");
            }
        });
    }
}

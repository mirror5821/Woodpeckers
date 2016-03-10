package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class LoginActivity extends BaseActivity {
    private EditText mEtName,mEtPass;
    private Button mBtnRegister,mBtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setBack();
        setTitleText("登录");


        mEtName = (EditText)findViewById(R.id.name);
        mEtPass = (EditText)findViewById(R.id.pass);

        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnRegister = (Button)findViewById(R.id.btn);

        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
        }
    }

    public void login(){
        final String name = mEtName.getText().toString().trim();
        final String pass = mEtPass.getText().toString().trim();

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

        ap.postData1(LOGIN_TEST, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                SharePreferencesUtil.saveLoginInfo(getApplicationContext(),name,pass);
                SharePreferencesUtil.saveUserInfo(getApplicationContext(),data);

                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }
}

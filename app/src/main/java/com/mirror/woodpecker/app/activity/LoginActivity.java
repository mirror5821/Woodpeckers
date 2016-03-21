package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
        mEtName.setText("kefu");
        mEtPass.setText("asd123456");

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

        System.out.println("----------"+jb.toString());
        ap.postData1(LOGIN_TEST, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                /**
                 * 返回的数据中role_id为角色ID，数据1代表单位主管，
                 * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
                 */
                SharePreferencesUtil.saveLoginInfo(getApplicationContext(), name, pass);
                SharePreferencesUtil.saveUserInfo(getApplicationContext(), data);
                User user= SharePreferencesUtil.getUserInfo(getApplicationContext());
                AppContext.USER_ROLE_ID = user.getRole_id();
                AppContext.USER_ID = user.getId();

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
}

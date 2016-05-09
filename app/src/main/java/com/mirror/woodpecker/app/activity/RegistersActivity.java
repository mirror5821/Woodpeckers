package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Units;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.UIHelper;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class RegistersActivity extends BaseActivity {
    private RadioGroup mRg;
    private RadioButton mRb1,mRb2;
    private TextView mTvSignUnit;
    private EditText mEtName,mEtPass,mEtPass2,mEtPhone,mEtMail;
    private Button mBtnRegister,mBtnLogin;
    private Button mBtnCode;
    private EditText mEtCode;
    private TextView mTvForget;

    private List<Units> mLists = new ArrayList<>();
    private int mUnitId = -201;
    private boolean mIsUnit = false;//是否选择签约单位
    private boolean mIsCode = false;//是否获取验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regsters);
        setBack();
        setTitleText("登录");

        mEtName = (EditText)findViewById(R.id.name);
        mEtPass = (EditText)findViewById(R.id.pass);
        mEtPass2 = (EditText)findViewById(R.id.pass2);
        mEtPhone = (EditText)findViewById(R.id.phone);
        mEtMail = (EditText)findViewById(R.id.mail);

        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnRegister = (Button)findViewById(R.id.btn);
        mBtnCode = (Button)findViewById(R.id.btn_code);
        mEtCode = (EditText) findViewById(R.id.e_code);
        mBtnCode.setOnClickListener(this);
        mTvForget = (TextView)findViewById(R.id.tv_forget);

        mTvForget.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);

        mTvSignUnit = (TextView)findViewById(R.id.sign_unit);
        mTvSignUnit.setOnClickListener(this);

        mRg = (RadioGroup)findViewById(R.id.rg);
        mRb1 = (RadioButton)findViewById(R.id.rb1);
        mRb2 = (RadioButton)findViewById(R.id.rb2);

        mRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb2) {
                    mIsUnit = true;
                    mTvSignUnit.setVisibility(View.VISIBLE);
                } else {
                    mIsUnit = false;
                    mTvSignUnit.setVisibility(View.GONE);
                }
            }
        });
        mRb1.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.sign_unit:
                loadUnit();
                break;
            case R.id.btn:
                sub();
                break;
            case R.id.btn_code:
                getPhoneCode();
                break;
            case R.id.tv_forget:
                startActivity(new Intent(RegistersActivity.this,ForgetPassActivity.class));
                break;
        }
    }

    private void initSelectUnitView(){
        UIHelper uiHelper = new UIHelper();
        uiHelper.initSelectUnitView(RegistersActivity.this,mLists, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                Units u = mLists.get(position);
                mTvSignUnit.setText(u.getName());
                mUnitId = u.getId();

            }
        });

        cancelProgressDialog();
    }


    private void loadUnit(){
        showProgressDialog("正在加载数据");
        if(mLists.size()>0){
            initSelectUnitView();
        }else{
            mHttpClient.postData(REPAIR_COM, null, new AppAjaxCallback.onRecevierDataListener<Units>() {

                @Override
                public void onReceiverData(List<Units> data, String msg) {
                    mLists.addAll(data);
                    initSelectUnitView();
                }

                @Override
                public void onReceiverError(String msg) {
                    showToast(msg);
                    cancelProgressDialog();
                }

                @Override
                public Class<Units> dataTypeClass() {
                    return Units.class;
                }
            });
        }

    }


    private void sub(){
        String name = mEtName.getText().toString().trim();
        String pass = mEtPass.getText().toString().trim();
        String pass2 = mEtPass2.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String mail = mEtMail.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            showToast("请输入注册邮箱或手机号码");
            return;
        }

        if(TextUtils.isEmpty(pass)){
            showToast("请输入密码");
            return;
        }

        if(TextUtils.isEmpty(pass2)){
            showToast("请输入重复密码");
            return;
        }

        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号码");
            return;
        }

        if(TextUtils.isEmpty(mail)){
            showToast("请输入注册邮箱");
            return;
        }

        if(!pass.equals(pass2)){
            showToast("两次密码不一致");
            return;
        }

        showProgressDialog("正在注册。。。");
        checkCodeByNet();
    }


    private void sub2(){
        String name = mEtName.getText().toString().trim();
        String pass = mEtPass.getText().toString().trim();
        String pass2 = mEtPass2.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();
        String mail = mEtMail.getText().toString().trim();



       /* *//**

         用户名	username
         密码	password
         确认密码	repassword
         手机	mobile
         邮箱	email
         签约类型	sign   默认是0即非签约，1是签约
         签约单位id	company
         验证码	verify
         */
        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("username", name);
            jb.put("password", pass);
            jb.put("repassword",pass2);
            jb.put("mobile",phone);
            jb.put("email", mail);



            if(mIsUnit){
                if(mUnitId == -201){
                    showToast("请选择签约单位");
                    return;
                }
                jb.put("sign",1);

            }else{
                jb.put("sign",0);
                mUnitId = 0;
            }
            jb.put("company",mUnitId);

        }catch (JSONException e){
            cancelProgressDialog();
        }

        ap.postData1(REGISTERS, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                cancelProgressDialog();

                startActivity(new Intent(RegistersActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
                cancelProgressDialog();
            }
        });
    }





    /**
     * 获取验证码
     */
    private void getPhoneCode(){
        String phone = mEtPhone.getText().toString().trim();

        if(TextUtils.isEmpty(phone)){
            showToast("请输入手机号码");
            return;
        }
        SMSSDK.getVerificationCode("86",phone);

        startCountDown();
    }

    private final Handler mHandler = new Handler() {
        @SuppressWarnings("unchecked")
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    private int mSeconds = 60;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private void startCountDown() {

        mSeconds = 60;
        stop();
        mBtnCode.setEnabled(false);
        mTimer = new Timer();
        mTimerTask = new TimerTask() {

            @Override
            public void run() {
                mSeconds--;
                mHandler.post(new Runnable() {

                    @Override
                    public void run() {
                        if (mSeconds > 0) {
                            mBtnCode.setText(mSeconds + "秒\n后重发");
                        } else {

                            stop();
                        }
                    }
                });
            }
        };
        mTimer.schedule(mTimerTask, 0, 1000);
    }


    private void stop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
        }

        mBtnCode.setText("重发");
        mBtnCode.setEnabled(true);
    }


    private void checkCodeByNet(){
        if(mIsCode){
            sub2();
            return;
        }
        String code = mEtCode.getText().toString().trim();
        String phone = mEtPhone.getText().toString().trim();

        if(TextUtils.isEmpty(code)){
            showToast("请填写验证码");
            return;
        }
        String url = "https://webapi.sms.mob.com/sms/verify";
//        String requsetStr = "appkey=11e4b9c5f05c8&amp;phone=18837145615&amp;zone=86&amp;&amp;code="+code;
        RequestParams rq = new RequestParams(url);
        rq.addParameter("appkey","11e4b9c5f05c8");
        rq.addParameter("phone",phone);
        rq.addParameter("zone","86");
        rq.addParameter("code",code);

        x.http().post(rq, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jb = new JSONObject(result);
                    int code = jb.getInt("status");
                    if(code == 200){
                        mIsCode = true;
                        sub2();
                    }else {
                        showToast("验证码错误");
                        cancelProgressDialog();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    cancelProgressDialog();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast("验证验证码失败");
                cancelProgressDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}

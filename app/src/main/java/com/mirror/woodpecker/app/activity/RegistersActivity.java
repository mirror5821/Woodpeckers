package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/3.
 */
public class RegistersActivity extends BaseActivity {
    private RadioGroup mRg;
    private RadioButton mRb1,mRb2;
    private TextView mTvSignUnit;
    private EditText mEtName,mEtPass,mEtPass2,mEtPhone,mEtMail;
    private Button mBtnRegister,mBtnLogin;

    private List<Units> mLists = new ArrayList<>();
    private int mUnitId = -201;
    private boolean mIsUnit = false;//是否选择签约单位
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



        /**

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

        }

        ap.postData1(REGISTERS, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }
}

package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class CommentActivity extends BaseActivity {
    private EditText mEt;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        setBack();
        setTitleText("评价");

        mEt = (EditText)findViewById(R.id.et);
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
            case R.id.right_text:
                startActivity(new Intent(CommentActivity.this,ComplainListActivity.class));
                break;
        }
    }

    private void sub(){
        String tips = mEt.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入评价内容");
            return;
        }

        JSONObject jb = new JSONObject();
        try{
            /**
             * 用户登录ID	uid
             订单id	order_id
             评论内容	content
             */
            jb.put("uid", AppContext.USER_ID);
            jb.put("order_id",getIntent().getIntExtra(INTENT_ID,0));
            jb.put("content",tips);
        }catch (JSONException e){

        }
        System.out.println("-------------"+jb.toString());
        mHttpClient.postData1(ORDER_COMMENT, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                finish();
            }

            @Override
            public void onError(String msg) {

                showToast(msg);
            }
        });
    }


}

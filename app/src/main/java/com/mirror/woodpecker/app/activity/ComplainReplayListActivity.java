package com.mirror.woodpecker.app.activity;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Complain;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class ComplainReplayListActivity extends BaseRecyclerViewActivity {
    private EditText mEt;
    private Button mBtn;

    private int mId;
    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("建议列表-回复列表");
        setBack();

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
        }
    }


    private void sub(){
        String tip = mEt.getText().toString();
        if(TextUtils.isEmpty(tip)){
            showToast("请输入回复的内容");
            return;
        }
        JSONObject jb = new JSONObject();
        try{
            jb.put("uid", AppContext.USER_ID);
            jb.put("id",mId);
            jb.put("content",tip);

        }catch (JSONException e){

        }

        mHttpClient.postData1(COMPLAIN_REPLAY, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                mEt.setText("");
                loadData();
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });

    }
    @Override
    public int setLayoutById() {
        mId = getIntent().getIntExtra(INTENT_ID,0);
        mList = new ArrayList<>();
        return R.layout.activity_complain_replay_list;
    }

    @Override
    public void loadData() {
        JSONObject jb = new JSONObject();
        try{
            jb.put("id",mId);
        }catch (JSONException e){

        }
        mHttpClient.postData(COMPLAIN_REPLAY_LIST, jb.toString(), new AppAjaxCallback.onRecevierDataListener<Complain>() {

            @Override
            public void onReceiverData(List<Complain> data, String msg) {
                if(mDefaultPage == pageNo){
                    mList.clear();
                }
                mList.addAll(data);
                setAdapter();
//                mRecyclerView.setPullRefreshEnabled(false);
                mRecyclerView.setLoadingMoreEnabled(false);
                mRecyclerView.refreshComplete();

            }

            @Override
            public void onReceiverError(String msg) {
                setAdapter();
            }

            @Override
            public Class<Complain> dataTypeClass() {
                return Complain.class;
            }
        });
    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_complain;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        Complain c = (Complain)item;
        String html = "<font color='#121661'>"+c.getUname()+"</font>:留言";
        TextView name = holder.getView(R.id.name);
        TextView content = holder.getView(R.id.content);
        TextView time = holder.getView(R.id.time);

        name.setText(Html.fromHtml(html));
        content.setText(c.getContent());
        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd MM:ss", c.getAddtime()));
    }
}

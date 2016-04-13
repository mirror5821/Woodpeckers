package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.ImageAddsAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.iface.DialogInterface;
import com.mirror.woodpecker.app.model.Kefu;
import com.mirror.woodpecker.app.model.Units;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.UIHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016/3/16.
 */
public class PayActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private NoScrollGridView mGridView;
    private EditText mEtPhone,mEtPrice;
    private TextView mTvKefu;
    private Button mBtn;

    private ImageAddsAdapter mAdapter;
    private List<String> mList;

    private ImageTools mImageTools;

    private int mKefuId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        setBack();
        setTitleText("付款凭证");

        mList = new ArrayList<>();
        mList.add(null);

        mImageTools = new ImageTools(this);

        mEtPhone = (EditText)findViewById(R.id.phone);
        mEtPrice = (EditText)findViewById(R.id.price);
        mTvKefu = (TextView)findViewById(R.id.kefu);
        mTvKefu.setOnClickListener(this);
        mBtn = (Button)findViewById(R.id.btn);
        mGridView = (NoScrollGridView)findViewById(R.id.gridview);
        mAdapter = new ImageAddsAdapter(getApplicationContext(),mList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn:
                sub();
                break;
            case R.id.kefu:
                loadUnit();
                break;
        }
    }

    private void sub(){
        String phone = mEtPhone.getText().toString();
        String price = mEtPrice.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast("请输入电话");
            return;
        }
        if(mKefuId == -1){
            showToast("请选择客服人员");
            return;
        }
        if(TextUtils.isEmpty(price)){
            showToast("请输入金额");
            return;
        }
        if(mList.size()<=1){
            showToast("请拍摄维修前照片");
            return;
        }

        showProgressDialog("正在提交");
        JSONObject jb = new JSONObject();
        try{
            /**
             * 登录ID	uid
             客服ID	kefuid
             打款金额	money
             上传图片	playurl
             电话	phone
             */
            jb.put("kefuid", mKefuId);
            jb.put("uid", AppContext.USER_ID);
            jb.put("playurl",mImageTools.filePathToString(mList.get(0)));
            jb.put("money",price);
            jb.put("phone",phone);
        }catch (JSONException e){

        }


        mHttpClient.postData1(PAY_MENT, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                cancelProgressDialog();
                finish();
            }

            @Override
            public void onError(String msg) {

                showToast(msg);
                cancelProgressDialog();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //表示点击上传照片的按钮
        if(position == mList.size()-1){
            openImage();
//            mImageTools.showGetImageDialog("选择照片的方式!");
        }else{//这里应该做其他动作
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }


    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 2;
    private void openImage(){
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;;
//        selectedMode = MultiImageSelectorActivity.MODE_SINGLE;


        int maxNum = 1;
        Intent intent = new Intent(PayActivity.this, MultiImageSelectorActivity.class);
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
                mList.clear();
                mAdapter.notifyDataSetChanged();

                mList.addAll(mSelectPath);
                mList.add(null);

                mAdapter.notifyDataSetChanged();
            }
        }
    }


    private List<Kefu> mLists = new ArrayList<>();
    private void initSelectUnitView(){
        UIHelper uiHelper = new UIHelper();
        uiHelper.initSelectUnitView(PayActivity.this,mLists, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                Kefu u = mLists.get(position);
                mTvKefu.setText(u.getUsername());
                mKefuId = u.getId();

            }
        });

        cancelProgressDialog();
    }


    private void loadUnit(){
        showProgressDialog("正在加载数据");
        if(mLists.size()>0){
            initSelectUnitView();
        }else{
            mHttpClient.postData(KEFU_LIST, null, new AppAjaxCallback.onRecevierDataListener<Kefu>() {

                @Override
                public void onReceiverData(List<Kefu> data, String msg) {
                    mLists.addAll(data);
                    initSelectUnitView();
                }

                @Override
                public void onReceiverError(String msg) {
                    showToast(msg);
                    cancelProgressDialog();
                }

                @Override
                public Class<Kefu> dataTypeClass() {
                    return Kefu.class;
                }
            });
        }

    }


}

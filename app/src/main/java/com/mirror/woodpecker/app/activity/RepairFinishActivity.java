package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.ImageAddsAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Repair;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016/3/18.
 */
public class RepairFinishActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private NoScrollGridView mGridView;
    private EditText mEt;
    private EditText mEtName;
    private EditText mEtDes;
    private EditText mEtJiejue;
    private Button mBtn;

    private ImageAddsAdapter mAdapter;
    private List<String> mList;

    private ImageTools mImageTools;

    private Repair mRepair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_feedback2);

        setBack();
        setTitleText("维修-解决中");

        mRepair = getIntent().getParcelableExtra(INTENT_ID);
        mList = new ArrayList<>();
        mList.add(null);

        mImageTools = new ImageTools(this);

        mEt = (EditText)findViewById(R.id.et);
        mEtName = (EditText)findViewById(R.id.name);
        mEtDes = (EditText)findViewById(R.id.dec);
        mEtJiejue = (EditText)findViewById(R.id.jiejue);

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
        }
    }

    private void sub(){
        String tips = mEt.getText().toString();
        String name = mEtName.getText().toString();
        String des = mEtDes.getText().toString();
        String jiejue = mEtJiejue.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入提示语");
            return;
        }

        if(TextUtils.isEmpty(name)){
            showToast("请输入设备名称");
            return;
        }

        if(TextUtils.isEmpty(des)){
            showToast("请输入故障描述");
            return;
        }

        if(TextUtils.isEmpty(jiejue)){
            showToast("请输入解决方案");
            return;
        }

        if(mList.size()<=1){
            showToast("请拍摄维修前照片");
            return;
        }

        JSONObject jb = new JSONObject();
        try{
            /**
             *
             订单id	order_id
             登录id	uid
             要更改的状态	status               8
             进度提示语	tips
             设备名称	device_name
             具体故障描述	detail_desc
             解决方案	solution_method
             维修后照片	repairimg
             */
            jb.put("order_id", mRepair.getOrder_id());
            jb.put("uid", AppContext.USER_ID);
            jb.put("status",8);
            jb.put("device_name",name);
            jb.put("detail_desc",des);
            jb.put("solution_method",jiejue);
            jb.put("action","done");

            jb.put("repairimg",mImageTools.filePathToString(mList.get(0)));
            jb.put("tips",tips);

        }catch (JSONException e){

        }


        System.out.println("-------------"+jb.toString());
        mHttpClient.postData1(ORDER_FLOW, jb.toString(), new AppAjaxCallback.onResultListener() {
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
        Intent intent = new Intent(RepairFinishActivity.this, MultiImageSelectorActivity.class);
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


}

package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016/3/18.
 */
public class RepairChangeActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private EditText mEtType;
    private EditText mEtName;
    private EditText mEtDes;
    private EditText mEtPrice;
    private Button mBtn;
    private NoScrollGridView mGridView;


    private Repair mRepair;

    private ImageAddsAdapter mAdapter;
    private List<String> mList;

    private ImageTools mImageTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_change);

        setBack();
        setTitleText("维修-解决中");

        mRepair = getIntent().getParcelableExtra(INTENT_ID);

        mEtName = (EditText)findViewById(R.id.name);
        mEtDes = (EditText)findViewById(R.id.dec);
        mEtPrice = (EditText)findViewById(R.id.price);
        mEtType = (EditText)findViewById(R.id.type);
        mBtn = (Button)findViewById(R.id.btn);

        mBtn.setOnClickListener(this);


        mImageTools = new ImageTools(this);
        mList = new ArrayList<>();
        mList.add(null);

        mGridView = (NoScrollGridView)findViewById(R.id.gridview);
        mAdapter = new ImageAddsAdapter(getApplicationContext(),mList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
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

    //写数据到SD中的文件
    public void writeFileSdcardFile(String fileName,String write_str) throws IOException {
        try{

            File file = new File(Environment.getExternalStorageDirectory(),
                    fileName);
            FileOutputStream fout = new FileOutputStream(file);
            byte [] bytes = write_str.getBytes();

            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void sub(){
        final String tips = mEtDes.getText().toString();
        final String name = mEtName.getText().toString();
        final String type = mEtType.getText().toString();
        final String price = mEtPrice.getText().toString();
        if(TextUtils.isEmpty(tips)){
            showToast("请输入提示语");
            return;
        }

        if(TextUtils.isEmpty(name)){
            showToast("请输入设备名称");
            return;
        }

        if(TextUtils.isEmpty(type)){
            showToast("请输入故障描述");
            return;
        }

        if(TextUtils.isEmpty(price)){
            showToast("请输入解决方案");
            return;
        }

        showProgressDialog("正在提交");
        new Thread(){
            @Override
            public void run() {
                super.run();

                JSONObject jb = new JSONObject();
                try{
                    /**
                     *
                     需调货（adjust_device）
                     登录维修人员ID	uid
                     要更改的状态	status               6
                     订单ID	order_id
                     设备名称	name
                     设备型号	type
                     设备价格	price
                     进度提示语	tips
                     */
                    jb.put("order_id", mRepair.getOrder_id());
                    jb.put("uid", AppContext.USER_ID);
                    jb.put("status",6);
                    jb.put("name",name);
                    jb.put("type",type);
                    jb.put("price",price);
                    jb.put("action","adjust_device");
                    jb.put("tips",tips);

                    JSONArray jaa = new JSONArray();
                    for (int i = 0;i<mList.size()-1;i++){
                        JSONObject jj = new JSONObject();
                        jj.put("image",mImageTools.filePathToString(mList.get(i)));//\\\
//                        jj.put("image",mImageTools.filePathToString(mList.get(i)).replace("\\\\\\/","/"));
//                        jj.put("image","我是图片流"+i);
                        jaa.put(jj);
                    }
                    jb.put("devimg",jaa.toString());

                    try {
                        writeFileSdcardFile("ccc.txt",jb.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        showToast("写入失败"+e.getLocalizedMessage());
                    }

                }catch (JSONException e){

                }


                Message message = Message.obtain();

                Bundle b = new Bundle();
                b.putString(INTENT_ID, jb.toString());
                message.setData(b);

//                message.obj = jb.toString();
                message.what = 1;
                mHandler.sendMessage(message);

            }
        }.start();


        /*JSONObject jb = new JSONObject();
        try{
            *//**
             *
             需调货（adjust_device）
             登录维修人员ID	uid
             要更改的状态	status               6
             订单ID	order_id
             设备名称	name
             设备型号	type
             设备价格	price
             进度提示语	tips
             *//*
            jb.put("order_id", mRepair.getOrder_id());
            jb.put("uid", AppContext.USER_ID);
            jb.put("status",6);
            jb.put("name",name);
            jb.put("type",type);
            jb.put("price",price);
            jb.put("action","adjust_device");
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
        });*/
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 1:

                    Bundle b = msg.getData();

                    mHttpClient.postData1(ORDER_FLOW,b.getString(INTENT_ID), new AppAjaxCallback.onResultListener() {
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
                    break;

            }
        }
    };

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


        int maxNum = 5;
        Intent intent = new Intent(RepairChangeActivity.this, MultiImageSelectorActivity.class);
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

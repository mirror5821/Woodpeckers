package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.iflytek.util.VoiceResult;
import com.iflytek.util.VoiceToTxUtil;
import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.ImageAddsAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.cache.PhoneCache;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.DBUtil;

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
 * Created by 王沛栋 on 2016/3/10.
 */
public class RepairAddActivity extends BaseActivity implements AdapterView.OnItemClickListener{
    private AutoCompleteTextView mEtPhone;
    private EditText mEtDes;
    private EditText mTvLoc;
    private Button mBtnPhone;
    private Button mBtn;
    private ImageView mImgMc;

    private NoScrollGridView mGridView;
    private ImageAddsAdapter mAdapter;
    private List<String> mList;

    private ImageTools mImageTools;

    private AutoCompleteTextView autotext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_add);
        setBack();
        setTitleText("故障申报");

        mEtPhone = (AutoCompleteTextView)findViewById(R.id.phone);
        mEtDes = (EditText)findViewById(R.id.dec);

        mTvLoc = (EditText) findViewById(R.id.loc);
        mBtn = (Button)findViewById(R.id.btn);
        mBtnPhone = (Button)findViewById(R.id.btn_phone);
        mImgMc = (ImageView)findViewById(R.id.img_mc);
        mImgMc.setOnClickListener(this);

        mBtnPhone.setOnClickListener(this);
        mBtn.setOnClickListener(this);
//        mTvLoc.setOnClickListener(this);


        List<PhoneCache> phoneCaches = DBUtil.getPhoneCache();
        if(phoneCaches != null){
            int l = phoneCaches.size();
            String []  autoStrings = new String [l];
            for (int i=0;i<l;i++){
                autoStrings[i] = phoneCaches.get(i).getNum();
            }

            //设置数据源
//            String[] autoStrings=new String[]{"15555","135665","beijing","london","Seoul Special","Los Angeles"};
            //设置ArrayAdapter，并且设定以单行下拉列表风格展示（第二个参数设定）。
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(RepairAddActivity.this,
                    android.R.layout.simple_dropdown_item_1line, autoStrings);
            //设置AutoCompleteTextView的Adapter
            mEtPhone.setAdapter(adapter);
        }


        mList = new ArrayList<>();
        mList.add(null);

        mImageTools = new ImageTools(this);
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
//            case R.id.loc:
//                startActivityForResult(new Intent(RepairAddActivity.this,MapSelectActivity.class), MAP_REQUESTCODE);
//                break;
            case R.id.btn_phone:
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
                break;
            case R.id.img_mc:
                VoiceToTxUtil util = new VoiceToTxUtil(RepairAddActivity.this, new VoiceResult() {
                    @Override
                    public String getVoiceToTx(String msg) {
                        mEtDes.setText(msg);
                        return null;
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case MAP_REQUESTCODE:
                    Bundle mBundle = data.getExtras();
                    String provinceName = mBundle.getString(PROVINCE);
                    String cityName = mBundle.getString(CITY);
                    String areaName = mBundle.getString(DISTRICT);
                    String street = mBundle.getString(ADDRESS);
                    double lat = mBundle.getDouble(LAT);
                    double lng = mBundle.getDouble(LNG);

                    mTvLoc.setText(provinceName+cityName+areaName+street);
                    break;

                case 1:
                    //
                    if (resultCode == RESULT_OK) {
                        Uri contactData = data.getData();

                        Cursor cursor = getContentResolver().query(contactData, null, null, null,
                                null);
                        cursor.moveToFirst();
                        String num = this.getContactPhone(cursor).substring(0,11);
                        DBUtil.savePhoneCache(num);
                        mEtPhone.setText(num);
                    }
                    break;
                case REQUEST_IMAGE:
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    mList.clear();
                    mAdapter.notifyDataSetChanged();

                    mList.addAll(mSelectPath);
                    mList.add(null);

                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }


    private String getContactPhone(Cursor cursor) {
        int phoneColumn = cursor
                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
        int phoneNum = cursor.getInt(phoneColumn);
        String result = "";
        if (phoneNum > 0) {
            // 获得联系人的ID号
            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            String contactId = cursor.getString(idColumn);
            // 获得联系人电话的cursor
            Cursor phone = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                            + contactId, null, null);
            if (phone.moveToFirst()) {
                for (; !phone.isAfterLast(); phone.moveToNext()) {
                    int index = phone
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNumber = phone.getString(index);
                    String phoneName = phone.getString(0);
                    result = phoneNumber+"--"+phoneName;
                }
                if (!phone.isClosed()) {
                    phone.close();
                }
            }
        }
        return result;
    }

    private boolean isSub = false;
    public void sub(){

        if(isSub)
            return;

        isSub = true;
        final String phone = mEtPhone.getText().toString().trim();
        final String loc = mTvLoc.getText().toString().trim();
        final String des = mEtDes.getText().toString().trim();

        if(TextUtils.isEmpty(phone)){
            showToast("请输入联系方式");
            return;
        }

        if(TextUtils.isEmpty(loc)){
            showToast("请输入故障位置");
            return;
        }

        showProgressDialog("正在提交...");
        new Thread(){
            @Override
            public void run() {
                super.run();

                JSONObject jb = new JSONObject();
                try{
                    jb.put("phone", phone);
                    jb.put("gz_desc", des);
                    jb.put("gz_postion",loc);
                    jb.put("uid", AppContext.USER_ID);

                    JSONArray jaa = new JSONArray();
                    for (int i = 0;i<mList.size()-1;i++){
                        JSONObject jj = new JSONObject();
                        jj.put("image",mImageTools.filePathToString(mList.get(i)));//\\\
//                        jj.put("image",mImageTools.filePathToString(mList.get(i)).replace("\\\\\\/","/"));
//                jj.put("image","我是图片流"+i);
                        jaa.put(jj);
                    }
                    jb.put("upimg",jaa.toString());

                    try {
                        writeFileSdcardFile("qq.txt",jb.toString());
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




        /*if(TextUtils.isEmpty(des)){
            showToast("请输入故障描述");
            return;
        }*/

//        /**
//         电话	phone
//         故障位置	gz_postion
//         故障描述	gz_desc
//         登录用户ID		uid
//         */
//
//        AppHttpClient ap = new AppHttpClient();
//        JSONObject jb = new JSONObject();
//        try{
//            jb.put("phone", phone);
//            jb.put("gz_desc", des);
//            jb.put("gz_postion",loc);
//            jb.put("uid", AppContext.USER_ID);
//
//        }catch (JSONException e){
//
//        }
//
//        ap.postData1(REPAIR, jb.toString(), new AppAjaxCallback.onResultListener() {
//            @Override
//            public void onResult(String data, String msg) {
//                isSub = false;
//                showToast(msg);
//                startActivity(new Intent(RepairAddActivity.this,RepairDetailsActivity.class).putExtra(INTENT_ID,Integer.valueOf(data)));
//
//                DBUtil.savePhoneCache(phone);
//                finish();
//            }
//
//            @Override
//            public void onError(String msg) {
//                isSub = false;
//                showToast(msg);
//            }
//        });
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

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 1:

                    Bundle b = msg.getData();

                    mHttpClient.postData1(REPAIR,b.getString(INTENT_ID), new AppAjaxCallback.onResultListener() {
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
        Intent intent = new Intent(RepairAddActivity.this, MultiImageSelectorActivity.class);
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

    /*@Override
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
    }*/

}

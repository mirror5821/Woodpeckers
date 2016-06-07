package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 王沛栋 on 2016/3/10.
 */
public class RepairAddActivity extends BaseActivity{
    private EditText mEtPhone,mEtDes;
    private EditText mTvLoc;
    private Button mBtnPhone;
    private Button mBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_add);
        setBack();
        setTitleText("故障申报");

        mEtPhone = (EditText)findViewById(R.id.phone);
        mEtDes = (EditText)findViewById(R.id.dec);

        mTvLoc = (EditText) findViewById(R.id.loc);
        mBtn = (Button)findViewById(R.id.btn);
        mBtnPhone = (Button)findViewById(R.id.btn_phone);

        mBtnPhone.setOnClickListener(this);
        mBtn.setOnClickListener(this);
//        mTvLoc.setOnClickListener(this);
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
                        String num = this.getContactPhone(cursor);
                        mEtPhone.setText(num);
                        showToast(num);
                    }
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

    public void sub(){
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

        /*if(TextUtils.isEmpty(des)){
            showToast("请输入故障描述");
            return;
        }*/

        /**
         电话	phone
         故障位置	gz_postion
         故障描述	gz_desc
         登录用户ID		uid
         */

        AppHttpClient ap = new AppHttpClient();
        JSONObject jb = new JSONObject();
        try{
            jb.put("phone", phone);
            jb.put("gz_desc", des);
            jb.put("gz_postion",loc);
            jb.put("uid", AppContext.USER_ID);

        }catch (JSONException e){

        }

        ap.postData1(REPAIR, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                startActivity(new Intent(RepairAddActivity.this,RepairDetailsActivity.class).putExtra(INTENT_ID,Integer.valueOf(data)));
                finish();
            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }
}

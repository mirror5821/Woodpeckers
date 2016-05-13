package com.mirror.woodpecker.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.service.XunJianService;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.XunJianUtil;

import java.util.List;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianAlertActivity extends BaseActivity {
    private TextView mTv;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunjian);
        setTitleText("巡检");

        mTv = (TextView)findViewById(R.id.tv);
        intent = new Intent(XunJianAlertActivity.this, XunJianService.class);

        mHttpClient.postData(XUNJIAN_LIST, null, new AppAjaxCallback.onRecevierDataListener<XunJian>() {
            @Override
            public void onReceiverData(List<XunJian> data, String msg) {
                if(data !=null){
                    StringBuilder sb = new StringBuilder();
                    for(XunJian xj:data){
                        sb.append(xj.getProject_name()+" ");
                    }

                    startService(intent);
                    showNormalDialog("闹钟响了","项目"+sb.toString()+ "该巡检了", "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            stopService(intent);
                            finish();
                        }
                    });

                    mTv.setText(sb.toString());
                }else{
                    XunJianUtil.stopXunjian(XunJianAlertActivity.this);
                }

            }

            @Override
            public void onReceiverError(String msg) {
                showToast(msg);
            }

            @Override
            public Class<XunJian> dataTypeClass() {
                return XunJian.class;
            }
        });


    }
}

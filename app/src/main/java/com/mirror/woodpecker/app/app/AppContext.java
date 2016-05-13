package com.mirror.woodpecker.app.app;

//import com.baidu.mapapi.SDKInitializer;

import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.XunJianUtil;

import java.util.List;

import cn.smssdk.SMSSDK;
import dev.mirror.library.android.app.BaseAppContext;

/**
 * Created by dongqian on 16/1/3.
 */
public class AppContext extends BaseAppContext {
    //我的位置信息
    public static double Longitude =0;
    public static double Latitude = 0;
    public static String Address;
    public static String LOC_CITY = "郑州市";
    public static String LOC_AREA = "金水区";
    public static boolean IS_LOGIN = false;

    public static boolean IS_XUNJINNING = false;//

    /**
     * 返回的数据中role_id为角色ID，数据1代表单位主管，
     * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
     * 根据这个进行登录判定
     */
    public static int USER_ROLE_ID = -1;
    public static int USER_ID;

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(instance);

        //sharesdk短信
        SMSSDK.initSDK(this, "11e4b9c5f05c8", "e482c03620d532d19327073a3b001ea7");
        loadXunjian();
    }

    public static AppContext getInstance(){

        return instance;
    }


    private boolean isXunjian = false;
    private AppHttpClient mHttpClient;// = new AppHttpClient();
    public void loadXunjian() {
        if(mHttpClient == null){
            mHttpClient = new AppHttpClient();
        }
        mHttpClient.postData(Constants.XUNJIAN_LIST, null, new AppAjaxCallback.onRecevierDataListener<XunJian>() {
            @Override
            public void onReceiverData(List<XunJian> data, String msg) {
                if(data != null){
                    IS_XUNJINNING = true;
                    for(int i =0;i<data.size();i++){
                        XunJian xj = data.get(i);
                        if(xj.getStatus().equals("0")){
                            if(isXunjian){
                                break;
                            }

                            XunJianUtil.startXunjian(AppContext.this);
                            isXunjian = true;
                        }
                    }
                    if(!isXunjian){
                        XunJianUtil.stopXunjian(AppContext.this);
                    }
                }


            }

            @Override
            public void onReceiverError(String msg) {
            }

            @Override
            public Class<XunJian> dataTypeClass() {
                return XunJian.class;
            }
        });
    }
}

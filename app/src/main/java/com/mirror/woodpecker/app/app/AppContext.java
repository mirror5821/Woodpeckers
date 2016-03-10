package com.mirror.woodpecker.app.app;

import com.baidu.mapapi.SDKInitializer;

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


    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(instance);
    }

    public static AppContext getInstance(){

        return instance;
    }
}

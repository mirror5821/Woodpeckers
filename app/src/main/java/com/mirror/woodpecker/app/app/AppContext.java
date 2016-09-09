package com.mirror.woodpecker.app.app;

import android.media.MediaPlayer;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechUtility;
import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.model.XunJian;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppHttpClient;
import com.mirror.woodpecker.app.util.XunJianUtil;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
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

    private static MediaPlayer mMediaPlayer;// = MediaPlayer.create(context, R.raw.tx);

    /**
     * 返回的数据中role_id为角色ID，数据1代表单位主管，
     * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
     * 根据这个进行登录判定
     */
    public static int USER_ROLE_ID = -1;
    public static int USER_ID;

    private static AppContext instance;

    private static DbManager.DaoConfig daoConfig;
    public static DbManager.DaoConfig getDaoConfig() {
        if(daoConfig == null){
            daoConfig = new DbManager.DaoConfig()
                    .setDbName("zmn_db")//创建数据库的名称
                    .setDbVersion(1)//数据库版本号
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                            // TODO: ...
                            // db.addColumn(...);
                            // db.dropTable(...);
                            // ...
                        }
                    });//数据库更新操作
        }
        return daoConfig;
    }

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(AppContext.this, "appid=" + getString(com.iflytek.R.string.app_id));
        super.onCreate();

        instance = this;

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(instance);

        //sharesdk短信
        SMSSDK.initSDK(this, "11e4b9c5f05c8", "e482c03620d532d19327073a3b001ea7");
        loadXunjian();

        //初始化极光推送
        JPushInterface.setDebugMode(false); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }


    public static AppContext getInstance(){

        return instance;
    }


    /**
     * 推送重复的语音
     * @return
     */
    public static void startPushMediaPlayer(){
//        if(mMediaPlayer == null){
//            mMediaPlayer = MediaPlayer.create(getInstance(), R.raw.tx);
//            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    mp.start();
//                }
//            });
//        }

        mMediaPlayer = MediaPlayer.create(getInstance(), R.raw.tx);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
            }
        });
        mMediaPlayer.start();
    }

    /**
     * 推送的声音关闭
     */
    public static void stopPushMediaPlayer(){
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
        }
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


    /**
     * 加载网络头像
     * @param iv
     * @param url
     */
    public static void displayHeaderImage(ImageView iv, String url){
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(200,200)
                .setCircular(true)
                .setFadeIn(true)
                .setAutoRotate(true)
                .build();
        x.image().bind(iv, url,imageOptions);
    }

    /**
     * 加载本地图片 -- 头像
     * @param iv
     * @param fileUrl
     */
    public static void displayLocHeaderImage(ImageView iv,String fileUrl){
        File imageFile = new File(fileUrl);
        ImageOptions imageOptions = new ImageOptions.Builder()
                .setSize(200,200)
                .setCircular(true)
                .setFadeIn(true)
                .setAutoRotate(true)
                .build();
        x.image().bind(iv, imageFile.toURI().toString(), imageOptions);
    }


    /**
     * 初始化数据库
     */
    protected static DbManager db;
    protected static void initDb(){
        //本地数据的初始化
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("xutils3_db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreate(DbManager db, TableEntity<?> table) {
                        //balabala...
                    }
                })//设置数据库创建时的Listener
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        //balabala...
                    }
                });//设置数据库升级时的Listener,这里可以执行相关数据库表的相关修改,比如alter语句增加字段等
        //.setDbDir(null);//设置数据库.db文件存放的目录,默认为包名下databases目录下
        db = x.getDb(daoConfig);
    }

    public static  DbManager mDB(){
        try{
            if(mDB() == null){
                initDb();
            }
            return db;
        }catch (Exception e){

        }
        return null;
    }


}

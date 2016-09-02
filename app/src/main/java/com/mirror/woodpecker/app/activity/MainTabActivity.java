package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.widget.TabHost;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.fragment.IndexFragment;
import com.mirror.woodpecker.app.fragment.MyFragment;
import com.mirror.woodpecker.app.fragment.RepairRepairFragment;
import com.mirror.woodpecker.app.fragment.ServiceRepairFragment;
import com.mirror.woodpecker.app.iface.OnTabSelect;
import com.mirror.woodpecker.app.model.Version;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.AppUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/1/3.
 */
public class MainTabActivity extends BaseTabActivity implements OnTabSelect{
    private int mCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        checkVersion();
        //得判断第三选项卡中是否已经登陆
		mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(mTabs[1])) {
                    if(!AppContext.IS_LOGIN){
                        mCode = 1;
                        startActivityForResult(new Intent(MainTabActivity.this, Login2Activity.class), 7801);
                        onSelect(0);
                    }else{
                        if (AppContext.USER_ROLE_ID != 3) {
                            showToast("您无权限查看此处内容");
                            onSelect(0);
                        }
                    }

                } else if (tabId.equals(mTabs[2])) {
                    if(!AppContext.IS_LOGIN){
                        mCode = 2;
                        startActivityForResult(new Intent(MainTabActivity.this, Login2Activity.class), 7802);
                    }else {
                        if (AppContext.USER_ROLE_ID != 4) {
                            showToast("您无权限查看此处内容");
                            onSelect(0);
                        }
                    }

                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==Activity.RESULT_OK){
            if(mCode == 1){
                if(AppContext.USER_ROLE_ID != 3){
                   showToast("您无权限查看此处内容");
                }else{
                    onSelect(2);
                }
            }
            if(mCode == 2){
                if(AppContext.USER_ROLE_ID != 4){
                    showToast("您无权限查看此处内容");
                }else{
                    onSelect(3);
                }
            }
            /*if(AppContext.USER_ROLE_ID == 3){
                onSelect(1);
            }else if(AppContext.USER_ROLE_ID == 4){
                onSelect(2);
            }else{
                onSelect(0);
            }*/
        }
    }



    private String [] mTabs = {"首页","客服调度","维修维保","个人中心"};
    @Override
    public String[] setTabTitles() {
        return mTabs;
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab2, R.drawable.tab3, R.drawable.tab4};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
//        return new Class[]{IndexFragment.class,ServiceRepairRecyclerViewFragment.class,
//                RepairRepairRecyclerViewFragment.class,MyFragment.class};
        return new Class[]{IndexFragment.class,ServiceRepairFragment.class,
                RepairRepairFragment.class,MyFragment.class};
//        ServiceRepairFragment
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }

    @Override
    public void onSelect(int index) {
        mFragmentTabHost.setCurrentTab(index);
    }

    private long last = 0;
    @Override
    public void onBackPressed() {
        if (mFragmentTabHost.getCurrentTab() != 0) {
            mFragmentTabHost.setCurrentTab(0);
        } else {
            if (System.currentTimeMillis() - last > 2000) {
                showToast( "再按一次返回键退出");
                last = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }

//		if (System.currentTimeMillis() - last > 2000) {
//			showToast( "再按一次返回键退出");
//			last = System.currentTimeMillis();
//		} else {
//			super.onBackPressed();
//		}

    }



    private Version mApp;
    public void checkVersion(){

        mHttpClient.postData1(VERSION_CHECK, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mApp = JsonUtils.parseList(data,Version.class).get(0);


                int versionCode = AppUtil.getAppVersionCode(getApplication());

                /**
                 * 如果当前版本号小于更新版本号
                 */
                if(versionCode!=mApp.getSvnid()){
                    if(mApp.getIs_update()==0){
                        showNormalDialog("检测到新版本", "是否更新？", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                downLoadApk();
                            }
                        });
                    }else{
                        downLoadApk();
                    }

                }

            }



            @Override
            public void onError(String msg) {
            }
        });
    }

    private String mFileStr = Environment.getExternalStorageDirectory().getPath()+"/zmn.apk";
    public void downLoadApk(){
        showProgressDialog("正在下载新版本");
        final RequestParams params = new RequestParams(mApp.getDownurl());
        File f = new File(mFileStr);
        if(f.exists()){
            f.delete();
        }
        params.setSaveFilePath(mFileStr);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<File>() {

            @Override
            public void onSuccess(File result) {

                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/dyjh.apk"),
                        "application/vnd.android.package-archive");
                *//*intent.setDataAndType(Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
                        "application/vnd.android.package-archive");*/


                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);

                /* 调用getMIMEType()来取得MimeType */
                String type = "application/vnd.android.package-archive";
                /* 设置intent的file与MimeType */
                intent.setDataAndType(Uri.fromFile(result), type);

                startActivity(intent);

                cancelProgressDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                cancelProgressDialog();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}

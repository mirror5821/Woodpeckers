package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.widget.TabHost;
import android.widget.Toast;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.fragment.AutoScrollPagerFragment;
import com.mirror.woodpecker.app.fragment.IndexFragment;
import com.mirror.woodpecker.app.fragment.MyFragment;
import com.mirror.woodpecker.app.fragment.RepairRepairRecyclerViewFragment;
import com.mirror.woodpecker.app.fragment.ServiceRepairRecyclerViewFragment;
import com.mirror.woodpecker.app.iface.OnTabSelect;
import com.mirror.woodpecker.app.receiver.BackgroundDialerReceiver;
import com.mirror.woodpecker.app.service.XunJianService;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by dongqian on 16/1/3.
 */
public class MainTabActivity extends BaseTabActivity implements OnTabSelect{
    private int mCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        st();

        //得判断第三选项卡中是否已经登陆
		mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals(mTabs[1])) {
                    if(!AppContext.IS_LOGIN){
                        mCode = 1;
                        startActivityForResult(new Intent(MainTabActivity.this, Login2Activity.class), 7801);
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

    private void st(){
        Intent intent = new Intent(MainTabActivity.this, XunJianService.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainTabActivity.this, 0, intent, 0);

//        long firstTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
        long systemTime = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然会有8个小时的时间差
//        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        calendar.set(Calendar.MINUTE, 04);
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 选择的定时时间
        long selectTime = calendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > selectTime) {
            Toast.makeText(MainTabActivity.this,"设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            selectTime = calendar.getTimeInMillis();
        }
        // 计算现在时间到设定时间的时间差
//        long time = selectTime - systemTime;
//        firstTime += time;
//        System.out.println("------------------"+firstTime);
        long time = selectTime - systemTime;
        // 进行闹铃注册
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, time, sender);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0, 70000, sender);
        System.out.println("----------------"+systemTime+"---"+selectTime+"----"+time);
//        Toast.makeText(MainTabActivity.this,systemTime+"---"+selectTime, Toast.LENGTH_LONG).show();
    }



    private static final int INTERVAL = 1000 * 60 * 60 * 24;// 24h
    private void tt(){
        Intent intent = new Intent(getApplicationContext(), XunJianService.class);
        PendingIntent sender = PendingIntent.getBroadcast(MainTabActivity.this,
                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        // Schedule the alarm!
        AlarmManager am = (AlarmManager) getApplicationContext() .getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 00);
        calendar.set(Calendar.MILLISECOND, 0);

        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                INTERVAL, sender);
    }
    private void test(){
        //当前设备的时间
        Calendar mCal = Calendar.getInstance();
        Intent intent = new Intent(getApplicationContext(), XunJianService.class);
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0, intent,0);
        AlarmManager am = (AlarmManager) getApplicationContext()
                .getSystemService(Service.ALARM_SERVICE);
        mCal.set(Calendar.HOUR_OF_DAY,16);
        mCal.set(Calendar.MINUTE,28);
        //设置将在对应的时间启动
        am.set(AlarmManager.RTC_WAKEUP,mCal.getTimeInMillis(),pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 10000, pi);

        BackgroundDialerReceiver.startAlarmPush(getApplicationContext(),10000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==Activity.RESULT_OK){
            if(mCode == 1){
                if(AppContext.USER_ROLE_ID != 3){
                   showToast("您无权限查看此处内容");
                }
            }
            if(mCode == 2){
                if(AppContext.USER_ROLE_ID != 4){
                    showToast("您无权限查看此处内容");
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
        return new Class[]{IndexFragment.class,ServiceRepairRecyclerViewFragment.class,
                RepairRepairRecyclerViewFragment.class,MyFragment.class};
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
}

package com.mirror.woodpecker.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.mirror.woodpecker.app.receiver.ServerReceiver;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class ServerNotifUtil {

    public static void startXunjian(Context context){
        xunJian1(context);
    }

    private static void xunJian1(Context context){
        //第一个
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ServerReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);


        // 120秒后发送广播，然后每隔10秒重复发广播
        int triggerAtTime = (int) (SystemClock.elapsedRealtime() + 120 * 1000);
        int interval = 120 * 1000;

        // 注册以上创建的1个PendingIntent，每隔120秒重复发广播
        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                triggerAtTime, interval, alarmIntent);
    }


    public static void stopXunjian(Context context){
        stopXunJian1(context);

    }

    private static void stopXunJian1(Context context){
        Intent intent = new Intent(context,ServerReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }


}

package com.mirror.woodpecker.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mirror.woodpecker.app.activity.XunJianActivity;
import com.mirror.woodpecker.app.receiver.XunJianReceiver;

import java.util.Calendar;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianUtil {
    private static Calendar c = Calendar.getInstance();
    private static int day = 24*3600*1000;
    public static void startXunjian(Context context){
        int times = day;
        //取得设定的时间
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY,15);
        c.set(Calendar.MINUTE,10);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //指定闹钟设定时间到了要执行callalarm
        Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        //setRepeating()可让闹钟重复执行 */
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), times, sender);

    }

    public static void stopXunjian(Context context){
        Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}

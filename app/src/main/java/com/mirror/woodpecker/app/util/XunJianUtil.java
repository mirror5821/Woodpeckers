package com.mirror.woodpecker.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.mirror.woodpecker.app.activity.XunJianActivity;
import com.mirror.woodpecker.app.receiver.XunJianReceiver;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianUtil {
    private static Calendar c = Calendar.getInstance();
    private static int day = 24*3600*1000;
    public static void startXunjian(Context context){
        //获取系统时间
        long systemTime = System.currentTimeMillis();

        int times = day;

        // 这里时区需要设置一下，不然会有8个小时的时间差
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //取得设定的时间
        c.setTimeInMillis(System.currentTimeMillis());
        c.set(Calendar.HOUR_OF_DAY,16);
        c.set(Calendar.MINUTE,36);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // 选择的定时时间
        long selectTime = c.getTimeInMillis();

        long repeatTime;
        if(selectTime>systemTime){
            repeatTime = selectTime - systemTime;
            c.add(Calendar.DAY_OF_MONTH, 1);
        }else{
            repeatTime = AlarmManager.INTERVAL_DAY - (systemTime - selectTime);
        }
        //指定闹钟设定时间到了要执行callalarm
        Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 1, intent, 0);
        //setRepeating()可让闹钟重复执行 */
        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), times, sender);

        System.out.println("--------------"+repeatTime+"-----"+c.getTimeInMillis()+"----"+AlarmManager.INTERVAL_DAY+"----"+systemTime+"----"+(c.getTimeInMillis()-systemTime));
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, repeatTime, AlarmManager.INTERVAL_DAY, sender);

//        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis()-systemTime, AlarmManager.INTERVAL_DAY, sender);

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

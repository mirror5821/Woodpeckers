package com.mirror.woodpecker.app.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.mirror.woodpecker.app.receiver.XunJian2Receiver;
import com.mirror.woodpecker.app.receiver.XunJianReceiver;

import java.util.Calendar;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianUtil {
//    private static AlarmManager alarmMgr;
//    private static PendingIntent alarmIntent;
//
//    private static AlarmManager alarmMgr2;
//    public static void test(Context context){
//        //第一个
//        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context,XunJianReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY,10);
//        calendar.set(Calendar.MINUTE,8);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        //获取系统时间
//        long systemTime = System.currentTimeMillis();
//        long selectTime = calendar.getTimeInMillis();
//
//        if(selectTime<systemTime){
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        System.out.println("---------------------="+calendar.getTimeInMillis());
////        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*20,alarmIntent);
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
//
//    }
//    private static Calendar c = Calendar.getInstance();
//    private static int day = 24*3600*1000;
//    public static void startXunjian(Context context){
//        int roleId = SharePreferencesUtil.getUserRoldId(context);
//        System.out.println("-----------------------roleid==="+roleId);
//        if(roleId<3){
//            System.out.println("-----------------------roleid1==="+roleId);
//            return;
//        }
//        /*//获取系统时间
//        long systemTime = System.currentTimeMillis();
//
//        // 这里时区需要设置一下，不然会有8个小时的时间差
//        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//        //取得设定的时间
//        c.setTimeInMillis(System.currentTimeMillis());
//        c.set(Calendar.HOUR_OF_DAY,16);
//        c.set(Calendar.MINUTE,36);
//        c.set(Calendar.SECOND, 0);
//        c.set(Calendar.MILLISECOND, 0);
//
//        // 选择的定时时间
//        long selectTime = c.getTimeInMillis();
//
//        long repeatTime;
//        if(selectTime>systemTime){
//            repeatTime = selectTime - systemTime;
//            c.add(Calendar.DAY_OF_MONTH, 1);
//        }else{
//            repeatTime = AlarmManager.INTERVAL_DAY - (systemTime - selectTime);
//        }
//        //指定闹钟设定时间到了要执行callalarm
//        Intent intent = new Intent(context,XunJianReceiver.class);
//        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
//        //setRepeating()可让闹钟重复执行 *//*
//        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
////        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), times, sender);
//        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, repeatTime, AlarmManager.INTERVAL_DAY, sender);
//        System.out.println("--------------"+repeatTime);*/
//
//        //第一个
//        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context,XunJianReceiver.class);
//        alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY,15);
//        calendar.set(Calendar.MINUTE,10);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//
//        //获取系统时间
//        long systemTime = System.currentTimeMillis();
//        long selectTime = calendar.getTimeInMillis();
//
//        if(selectTime<systemTime){
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//        }
//
//        System.out.println("----------="+calendar.getTimeInMillis());
////        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60*24,alarmIntent);
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
////        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,10000,1000*60*20,alarmIntent);
//
//
//
//        //第二个
//        alarmMgr2 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTimeInMillis(System.currentTimeMillis());
//        calendar2.set(Calendar.HOUR_OF_DAY,15);
//        calendar2.set(Calendar.MINUTE,15);
//        calendar2.set(Calendar.SECOND, 0);
//        calendar2.set(Calendar.MILLISECOND, 0);
//
//        long selectTime2 = calendar2.getTimeInMillis();
//
//        if(selectTime2<systemTime){
//            calendar2.add(Calendar.DAY_OF_MONTH, 1);
//        }
//        System.out.println("----------2="+calendar2.getTimeInMillis());
//        alarmMgr2.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
//    }

    public static void startXunjian(Context context){
        xunJian1(context);
        xunjian2(context);
    }

    private static void xunJian1(Context context){
        //第一个
        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        //获取系统时间
        long systemTime = System.currentTimeMillis();
        long selectTime = calendar.getTimeInMillis();

        if(selectTime<systemTime){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        System.out.println("----------="+calendar.getTimeInMillis());
//        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),1000*60*60*24,alarmIntent);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
    }

    private static void xunjian2(Context context){
        long systemTime = System.currentTimeMillis();

        AlarmManager alarmMgr2 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, XunJian2Receiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,0,intent,0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY,15);
        calendar2.set(Calendar.MINUTE,0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        long selectTime2 = calendar2.getTimeInMillis();

        if(selectTime2<systemTime){
            calendar2.add(Calendar.DAY_OF_MONTH, 1);
        }
        alarmMgr2.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY,alarmIntent);
    }

    public static void stopXunjian(Context context){
        stopXunJian1(context);
        stopXunJian2(context);
        /*Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);*/
    }

    private static void stopXunJian1(Context context){
        Intent intent = new Intent(context,XunJianReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }

    private static void stopXunJian2(Context context){
        Intent intent = new Intent(context,XunJian2Receiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                context, 1, intent, 0);
        AlarmManager am;
        am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(sender);
    }
}

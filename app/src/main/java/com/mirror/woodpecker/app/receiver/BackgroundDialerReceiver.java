package com.mirror.woodpecker.app.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.mirror.woodpecker.app.activity.MainTabActivity;
import com.mirror.woodpecker.app.service.XunJianService;

import java.util.Calendar;

/**
 * Created by 王沛栋 on 2016/3/30.
 */
public class BackgroundDialerReceiver {
    /** 使用AlarmService获取推送信息
     */
    public static void startAlarmPush(Context context, long intervalMillis) {
        AlarmManager am = (AlarmManager) context
                .getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(context, XunJianService.class);
        PendingIntent p_intent = PendingIntent.getService(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, 0, intervalMillis, p_intent);
    }



    /** 停止获取推送信息的AlarmService
     */
    public static void stopAlarmPush(Context context) {
        AlarmManager am = (AlarmManager) context
                .getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent(context, XunJianService.class);
        PendingIntent p_intent = PendingIntent.getService(context, 1, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        am.cancel(p_intent);
    }

}

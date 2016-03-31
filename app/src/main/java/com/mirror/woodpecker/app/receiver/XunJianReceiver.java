package com.mirror.woodpecker.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mirror.woodpecker.app.activity.XunJianAlertActivity;

/**
 * Created by 王沛栋 on 2016/3/31.
 */
public class XunJianReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, XunJianAlertActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}

package com.mirror.woodpecker.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mirror.woodpecker.app.util.XunJianUtil;


/**
 * Created by 王沛栋 on 2016-05-13.
 * 开机启动项目
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            XunJianUtil.startXunjian(context);

//            Intent newIntent = new Intent(context, XunJianAlertActivity.class);
//            newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //注意，必须添加这个标记，否则启动会失败
//            context.startActivity(newIntent);
        }
    }
}

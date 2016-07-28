package com.mirror.woodpecker.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mirror.woodpecker.app.activity.MainTabActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.model.Message;
import com.mirror.woodpecker.app.util.ServerNotifUtil;

import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver{
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
            //send the Registration Id to your server...

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        	processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			try{
				if(AppContext.USER_ROLE_ID == 3){
					ServerNotifUtil.startXunjian(context);
				}
			}catch (Exception e){

			}

            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
//			MessageUtil.savePushMessage(bundle.getString(JPushInterface.EXTRA_ALERT),bundle.getString(JPushInterface.EXTRA_ALERT));
//			System.out.println("----------------" + bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE)+"---" + bundle.getString(JPushInterface.EXTRA_MESSAGE)+"&"+bundle.getString(JPushInterface.EXTRA_ALERT));
			System.out.println("[MyReceiver]"+printBundle(bundle)+"---"+bundle.getString(JPushInterface.EXTRA_ALERT)+"---"+bundle.getString(JPushInterface.EXTRA_MESSAGE)+"---"+bundle.getString(JPushInterface.EXTRA_EXTRA));
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			try{
				ServerNotifUtil.stopXunjian(context);
			}catch (Exception e){

			}

//        	//打开自定义的Activity
//        	Intent i = new Intent(context, SplashActivity.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);

			//打开自定义的Activity
			Intent i = new Intent(context, MainTabActivity.class);
			i.putExtras(bundle);
			//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
			Message message = new Message();
			message.setTitle(bundle.getString(JPushInterface.EXTRA_ALERT));
			message.setContent(bundle.getString(JPushInterface.EXTRA_ALERT));
			i.putExtra(Constants.INTENT_ID,message);
			i.putExtra("IS_PUSH",true);
			context.startActivity(i);


        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
        	boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
        	Log.w(TAG, "[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
        	Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }

	}

	// 打印所有的 intent extra 数据  在此输出所有数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			}else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			}
			else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
//		String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//		String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//		if (BaseActivity.isForeground) {
//
//			Intent msgIntent = new Intent(BaseActivity.MESSAGE_RECEIVED_ACTION);
//			msgIntent.putExtra(BaseActivity.KEY_MESSAGE, message);
//			if (!TextUtils.isEmpty(extras)) {
//				try {
//					JSONObject extraJson = new JSONObject(extras);
//					if (null != extraJson && extraJson.length() > 0) {
//						msgIntent.putExtra(BaseActivity.KEY_EXTRAS, extras);
//					}
//				} catch (JSONException e) {
//
//				}
//
//			}
//			context.sendBroadcast(msgIntent);
//		}else{
//			VoiceUtil.msgToVoice(context, message);
//		}
	}
}

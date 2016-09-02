package com.mirror.woodpecker.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.util.TxToVoiceUtil;
import com.mirror.woodpecker.app.activity.MainTabActivity;
import com.mirror.woodpecker.app.activity.RepairDetailsActivity;
import com.mirror.woodpecker.app.activity.ServiceRepairDetailsActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.model.Message;
import com.mirror.woodpecker.app.util.ServerNotifUtil;

import org.json.JSONObject;

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
				if(AppContext.USER_ROLE_ID == 3||AppContext.USER_ROLE_ID == 4){
					if(bundle.getString(JPushInterface.EXTRA_ALERT).equals("您有新订单，请及时处理！")||
							bundle.getString(JPushInterface.EXTRA_ALERT).equals("您有新的维修单！请尽快处理！")){
						TxToVoiceUtil voice = new TxToVoiceUtil();
//						voice.TxToVoice(context,"帅帅好帅");
						ServerNotifUtil.startXunjian(context);
					}

				}else{
				}
			}catch (Exception e){
				System.out.println("####################"+e.getLocalizedMessage());
			}

            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			System.out.println("[MyReceiver]"+printBundle(bundle)+"---"+bundle.getString(JPushInterface.EXTRA_ALERT)+"---"+bundle.getString(JPushInterface.EXTRA_MESSAGE)+"---"+bundle.getString(JPushInterface.EXTRA_EXTRA));
			//-------------------------{"orderid":435}  可设置消息获取方式-bundle.getString(JPushInterface.EXTRA_EXTRA)
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
			if(AppContext.USER_ROLE_ID == 3||AppContext.USER_ROLE_ID == 4){
				try{
					try{

						if(bundle.getString(JPushInterface.EXTRA_ALERT).equals("您有新订单，请及时处理！")||
								bundle.getString(JPushInterface.EXTRA_ALERT).equals("您有新的维修单！请尽快处理！")){
							JSONObject jb = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
							int orderId = jb.getInt("orderid");
							Intent i = null;
							if(AppContext.USER_ROLE_ID == 3){
								i = new Intent(context, ServiceRepairDetailsActivity.class);
							}else{
								i = new Intent(context, RepairDetailsActivity.class);
							}
							i.putExtra(Constants.INTENT_ID,orderId);
							i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
							Message message = new Message();
							message.setTitle(bundle.getString(JPushInterface.EXTRA_ALERT));
							message.setContent(bundle.getString(JPushInterface.EXTRA_ALERT));
							i.putExtra("IS_PUSH",true);
							context.startActivity(i);

						}

					}catch (Exception e){
					}
					ServerNotifUtil.stopXunjian(context);
				}catch (Exception e){

				}
			}else{
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
			}

//        	//打开自定义的Activity
//        	Intent i = new Intent(context, SplashActivity.class);
//        	i.putExtras(bundle);
//        	//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        	i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//        	context.startActivity(i);

			//打开自定义的Activity



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

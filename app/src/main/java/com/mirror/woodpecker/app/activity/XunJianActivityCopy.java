package com.mirror.woodpecker.app.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.receiver.XunJianReceiver;
import com.mirror.woodpecker.app.util.XunJianUtil;

import java.util.Calendar;

public class XunJianActivityCopy extends Activity {
    private TextView textView;
    private Button setbtn,unsetbtn;
    //重复闹钟的按钮和TextView   
    private Button setbtns,unsetbtns;
    private TextView textView1;
    private static final int ButtonAlarm =1;
    //铃声文件夹
    private String setAlarmFloder = "/sdcard/music/alarms";


    private static final String TEMP_1="temp_1";

    Calendar c = Calendar.getInstance();
    private static int timeID= 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xunjian_time);

        XunJianUtil.startXunjian(XunJianActivityCopy.this);
        textView = (TextView)findViewById(R.id.textview1);
        setbtn = (Button)findViewById(R.id.setclock);
        unsetbtn = (Button)findViewById(R.id.unsetclock);
        setbtns = (Button)findViewById(R.id.setclocks);
        unsetbtns = (Button)findViewById(R.id.unsetclocks);
        textView1 = (TextView)findViewById(R.id.textview4);

        //获取SharedPreferences
        SharedPreferences per = getSharedPreferences(TEMP_1, MODE_WORLD_READABLE);
        //得到text内容
        String text_1 =per.getString("text_1", "当前没有设置闹钟!");
        String text_2 =per.getString("text_2", "当前没有设置闹钟!");
        //在text中显示内容
        textView.setText(text_1);
        textView1.setText(text_2);
        //设置铃声按钮
        //setbell = (Button)findViewById(R.id.btn3);


        setbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 取得按下按钮的时间作为TimePickerDialog的默认值
                c.setTimeInMillis(System.currentTimeMillis());
                //定义获取时间
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                //跳出TimePickerDialog来设定时间
                new TimePickerDialog(XunJianActivityCopy.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // 取得设定后的时间 秒跟毫秒设为0
                                c.setTimeInMillis(System.currentTimeMillis());
                                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                //定闹钟设定时间到时要执行CallAlarm.class
                                Intent intent  = new Intent(XunJianActivityCopy.this,XunJianReceiver.class);
                                PendingIntent sender = PendingIntent.getBroadcast(XunJianActivityCopy.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                //获取AlarmManager 
                                //AlarmManager.RTC_WAKEUP设定服务在系统休眠时同样会执行
                                //
                                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                                //set()设定的PendingIntent只会执行一次
                                am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),sender);
                                //显示设定的闹钟时间
                                String tmpS = format(hourOfDay)+":"+ format(minute);
                                textView.setText(tmpS);
                                //toas显示设定的时间
                                Toast.makeText(XunJianActivityCopy.this, "设定的时间为:"+tmpS, Toast.LENGTH_LONG).show();
                            }
                        },mHour, mMinute, true).show();
            }
        });
        //取消闹钟

        unsetbtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new  Intent (XunJianActivityCopy.this,XunJianReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(XunJianActivityCopy.this, 0, intent, 0);
                //由AlarmManager中移除
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                textView.setText("当前没有设置闹钟!");
                //以Toast提示已删除设定，并更新显示的闹钟时间
                Toast.makeText(XunJianActivityCopy.this, "闹钟已经取消",Toast.LENGTH_LONG).show();
            }
        });


        //一下设置重复闹钟
        /* create重复响起的闹钟的设置画面 */  
        /* 引用timeset.xml为Layout */
        /* LayoutInflater用来找Layout下的xml文件，并且将xml文件实例化 */
        LayoutInflater layoutInflater =LayoutInflater.from(this);
        final View setView = layoutInflater.inflate(R.layout.activity_xunjian_time_dialog, null);
        final TimePicker tPicker = (TimePicker)setView.findViewById(R.id.tPicker);
        tPicker.setIs24HourView(true);

        final AlertDialog di = new AlertDialog.Builder(XunJianActivityCopy.this)
                .setTitle("设定").setView(setView).setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override

                    public void onClick(DialogInterface dialog, int which) {
                        // 取得设定的间隔秒数
                        EditText ed = (EditText)setView.findViewById(R.id.mEdit);
                        int times = Integer.parseInt(ed.getText().toString())*1000;
                        //取得设定的时间
                        c.setTimeInMillis(System.currentTimeMillis());
                        c.set(Calendar.HOUR_OF_DAY, tPicker.getCurrentHour());
                        c.set(Calendar.MINUTE, tPicker.getCurrentMinute());
                        c.set(Calendar.SECOND, 0);
                        c.set(Calendar.MILLISECOND, 0);
                        //指定闹钟设定时间到了要执行callalarm
                        Intent intent = new Intent(XunJianActivityCopy.this,XunJianReceiver.class);
                        PendingIntent sender = PendingIntent.getBroadcast(
                                XunJianActivityCopy.this, 1, intent, 0);
                        //setRepeating()可让闹钟重复执行 */
                        AlarmManager am;
                        am = (AlarmManager)getSystemService(ALARM_SERVICE);
                        am.setRepeating(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), times, sender);
	            /* 更新显示的设定闹钟时间 */
                        String tmps = format(tPicker.getCurrentHour())+":"+format(tPicker.getCurrentMinute());
                        textView1.setText(tmps);
	            /* 以Toast提示设定已完成 */
                        Toast.makeText(XunJianActivityCopy.this, "设定闹钟时间为" + tmps + "开始，重复间隔为" + times / 1000 + "秒", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                }).create();

        //重复响起的闹钟的设定Button
        setbtns.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 取得按下按钮时的时间做为tPicker的默认值
                c.setTimeInMillis(System.currentTimeMillis());
                tPicker.setCurrentHour(c.get(Calendar.HOUR_OF_DAY));
                tPicker.setCurrentMinute(c.get(Calendar.MINUTE));
                //跳出设定画面di
                di.show();

            }
        });
        //重复响起的闹钟的移除Button

        unsetbtns.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(XunJianActivityCopy.this,XunJianReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(
                        XunJianActivityCopy.this, 1, intent, 0);
                AlarmManager am;
                am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.cancel(sender);
                Toast.makeText(XunJianActivityCopy.this, "闹钟时间解除", Toast.LENGTH_LONG).show();
                textView1.setText("当前没有设置闹钟!");
            }
        });

//        setbell.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if (bFolder(setAlarmFloder)) {
//					//打开系统铃声设置
//					Intent intent =  new  Intent (RingtoneManager
//							.ACTION_RINGTONE_PICKER);
//					//设置铃声类型和title
//					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
//					intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "设置闹铃声音");
//					//设置完成后,返回当前的activity
//					startActivityForResult(intent, ButtonAlarm);
//				}
//			}
//		});

    }

    //当设置铃声之后的回调函数
//    @Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//    	if (requestCode != RESULT_OK) {
//			return;
//		}
//    	switch (requestCode) {
//		case ButtonAlarm:
//			Uri pickedUri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//			//将选择的铃声设置为默认
//			if (pickedUri != null) {
//				RingtoneManager.setActualDefaultRingtoneUri(XunJianActivity.this, RingtoneManager
//						.TYPE_ALARM, pickedUri);
//			}
//			break;
//
//		default:
//			break;
//		}
//    	
//    	
//		super.onActivityResult(requestCode, resultCode, data);
//	}

    //检测是否存在指定的文件夹,如果不存在,则创建
//    private boolean bFolder (String serbFolder){
//    	boolean btmp =false;
//    	File f = new File(serbFolder);
//    	if (!f.exists()) {
//			if (f.mkdirs()) {
//				btmp = true;
//			}else {
//				btmp = false;
//			}
//		}else {
//			btmp =true;
//		}
//    	return btmp;
//    }
//    



    private String format(int x)
    {
        String s = "" + x;
        if (s.length() == 1)
            s = "0" + s;
        return s;
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        //获取编译器
        SharedPreferences.Editor editor =
                getSharedPreferences(TEMP_1, MODE_WORLD_WRITEABLE).edit();
        //将text内容添加到编译器 
        editor.putString("text_1", textView.getText().toString());
        editor.putString("text_2", textView1.getText().toString());
        //提交编辑内容
        editor.commit();
    }
}
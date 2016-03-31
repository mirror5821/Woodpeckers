package com.mirror.woodpecker.app.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.mirror.woodpecker.app.R;

/**
 * Created by 王沛栋 on 2016/3/30.
 */
public class XunJianService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private MediaPlayer mMediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();

        mMediaPlayer = MediaPlayer.create(this, R.raw.tx);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mMediaPlayer.start();
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }

}

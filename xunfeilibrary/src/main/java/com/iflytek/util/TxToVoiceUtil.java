package com.iflytek.util;

import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

/**
 * Created by 王沛栋 on 2016-09-02.
 */
public class TxToVoiceUtil {
    public void TxToVoice(Context context,String tx){
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context,null);
        mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
        mTts.setParameter(SpeechConstant.SPEED,"50");
        mTts.setParameter(SpeechConstant.VOLUME,"80");//音量大小
        mTts.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.startSpeaking(tx,mSynListener);
    }

    public SynthesizerListener mSynListener = new SynthesizerListener() {
        @Override
        public void onSpeakBegin() {

        }

        @Override
        public void onBufferProgress(int i, int i1, int i2, String s) {

        }

        @Override
        public void onSpeakPaused() {

        }

        @Override
        public void onSpeakResumed() {

        }

        @Override
        public void onSpeakProgress(int i, int i1, int i2) {

        }

        @Override
        public void onCompleted(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
    };
}

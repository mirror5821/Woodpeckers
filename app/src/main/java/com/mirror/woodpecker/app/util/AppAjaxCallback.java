package com.mirror.woodpecker.app.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.List;

import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by mirror on 16/1/3.
 */
public class AppAjaxCallback<T> implements Callback.CommonCallback<String> {
    private onRecevierDataListener<T> mRecevierDataListener;
    private onResultListener mListener;

    public AppAjaxCallback(onRecevierDataListener<T> dataListener){
        mRecevierDataListener = dataListener;
    }

    public AppAjaxCallback(onResultListener dataListener){
        this.mListener = dataListener;
    }


    /**
     * 定义普通数据请求listener
     */
    public interface onResultListener{
        void onResult(String data, String msg);
        void onError(String msg);
    }

    /**
     * 定义list请求listener
     * @param <T>
     */
    public interface onRecevierDataListener<T>{
        void onReceiverData(List<T> data, String msg);
        void onReceiverError(String msg);
        Class<T> dataTypeClass();
    }

    private String mListNoData = "未查询到数据";


    private String listNoData = "未查询到任何数据";
    private String mErrorMsg = "网络错误,请检查您的网络!";
    @Override
    public void onSuccess(String result) {
        String t =  result.toString();
        System.out.println("----------------callback----"+t);
        if(!TextUtils.isEmpty(t)){
            try{
                JSONObject jb = new JSONObject(t);
                String status = jb.getString("code");

                String msg = jb.getString("msg");
                //如果==10001，表示查询错误
                if(!status.equals("10001")){
                    String data = jb.getString("data").toString();
                    //表示请求是列表数据
                    if(mRecevierDataListener !=null){
                        List<T> list = JsonUtils.parseList(data, mRecevierDataListener.dataTypeClass());
                        //如果数据不为空
                        if(list!=null){
                            mRecevierDataListener.onReceiverData(list, msg);
                        }else{
                            try{
                                //判断分页list的返回
                                JSONObject jb2 = new JSONObject(data);
                                if(!jb2.getString("list").equals(null)){
                                    mRecevierDataListener.onReceiverData(JsonUtils.parseList(jb2.getString("list"),
                                            mRecevierDataListener.dataTypeClass()),msg);
                                }else{
                                    mRecevierDataListener.onReceiverError(listNoData);
                                }
                            }catch(Exception e){
                                mRecevierDataListener.onReceiverError(listNoData);
                            }
//							mRecevieDataListener.onReceiverError("----"+msg);
                        }
                    }else{
                        mListener.onResult(data,msg);
                    }
                }else{//表示没有返回数据
                    if(mRecevierDataListener!=null){
                        mRecevierDataListener.onReceiverError(msg);
                    }else{
                        mListener.onError(msg);
                    }
                }
            }catch(JSONException e){
//				if(mRecevieDataListener!=null){
//					mRecevieDataListener.onReceiverError(e.getMessage());
//				}else{
//					mListener.onError(e.getMessage());
//				}
                if(mRecevierDataListener!=null){
                    mRecevierDataListener.onReceiverError(e.getLocalizedMessage());
                }else{
                    mListener.onError(e.getLocalizedMessage());
                }
            }
        }else{
            if(mRecevierDataListener!=null){
                mRecevierDataListener.onReceiverError(mErrorMsg);
            }else{
                mListener.onError(mErrorMsg);
            }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if(mRecevierDataListener!=null){
            mRecevierDataListener.onReceiverError(ex.getMessage());
        }else{
            mListener.onError(ex.getMessage());
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}

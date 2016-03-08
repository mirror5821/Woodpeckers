package com.mirror.woodpecker.app.util;

import com.mirror.woodpecker.app.model.Constants;

import org.xutils.x;

/**
 * Created by mirror on 16/1/3.
 */
public class AppHttpClient{
    public AppHttpClient(){

    }

    public static final String SERVIER_HEADER = Constants.HOST_HEADER;
    public void postData1(String fName,String p,AppAjaxCallback.onResultListener listener){
        StringBuilder sb = new StringBuilder();
        sb.append(SERVIER_HEADER);
        sb.append(fName);

        AppAjaxParam param = new AppAjaxParam(fName,sb.toString());
        param.addParameter(Constants.JSONG_KEY, p);
        x.http().post(param,new AppAjaxCallback<>(listener));

    }


    public <T> void postData(String fName,String p,AppAjaxCallback.onRecevierDataListener<T> listener){
        StringBuilder sb = new StringBuilder();
        sb.append(SERVIER_HEADER);
        sb.append(fName);

        AppAjaxParam param = new AppAjaxParam(fName,sb.toString());
        param.addParameter(Constants.JSONG_KEY, p);
        x.http().post(param, new AppAjaxCallback<>(listener));
    }


    public void uploadImg(String imgData,AppAjaxCallback.onResultListener listener) {

        StringBuilder sb = new StringBuilder();
        sb.append(Constants.HOST_IMG_HEADER);
//        sb.append(Constants.UPLOAD_IMG);
        AppAjaxParam param = new AppAjaxParam(sb.toString());

        param.addParameter("ImgData",imgData);
        x.http().post(param, new AppAjaxCallback<>(listener));
    }
}

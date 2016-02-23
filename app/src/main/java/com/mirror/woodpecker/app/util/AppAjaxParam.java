package com.mirror.woodpecker.app.util;

import com.mirror.woodpecker.app.model.Constants;

import org.xutils.http.RequestParams;

import dev.mirror.library.android.util.MD5Util;
import dev.mirror.library.android.util.PhoneUtil;

/**
 * Created by mirror on 16/1/3.
 */
public class AppAjaxParam extends RequestParams {
    public AppAjaxParam(String url){
        super(url, null, null, null);
    }

    public AppAjaxParam(String fName,String url){
        super(url, null, null, null);
        String appid = "999999";
        String chnl = "10086";
        String dev = PhoneUtil.MANUFACTURER+" "+PhoneUtil.MODEL;
        String m = fName;
        String os = "android";
        String t = "122545450";
        String ver = android.os.Build.VERSION.RELEASE;

        String secret = Constants.SECRET;
        String sign = MD5Util.stringToMd5("appid=" + appid + "&chnl=" + chnl + "&dev=" + dev + "&m=" + m + "&os=" + os + "&t=" + t + "&ver=" + ver + "&secret=" + secret);
        System.out.println("sign------------"+sign);

        this.addHeader("Connection", "keep-alive");
        this.addHeader("Content-Type", "application/json");//multipart/form-data
        this.addHeader("appid", appid);
        this.addHeader("chnl", chnl);
        this.addHeader("dev", dev);
        this.addHeader("os", os);
        this.addHeader("os_ver", PhoneUtil.VERSION_RELEASE);
        this.addHeader("sign", sign);
        this.addHeader("t", t);
        this.addHeader("ver", ver);
        this.addHeader("token", "userid");
        this.addHeader("m", fName);

    }


}

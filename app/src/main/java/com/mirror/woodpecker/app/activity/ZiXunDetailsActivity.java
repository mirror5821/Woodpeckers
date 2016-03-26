package com.mirror.woodpecker.app.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.About;
import com.mirror.woodpecker.app.model.Complain;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import java.util.List;

import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class ZiXunDetailsActivity extends BaseActivity {
    private WebView mWebView;

    private String mHtml;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_details);
        setBack();
        setTitleText("业务咨询");
        mWebView = (WebView)findViewById(R.id.webview);
        loadData();
    }


    private void loadData(){
        mHttpClient.postData1(ZI_XUN, null, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                About.Info info = JsonUtils.parse(data, About.Info.class);
                mHtml = info.getContent();
                showWebView();
            }

            @Override
            public void onError(String msg) {

            }
        });

    }

    private void showWebView() {
        // 设置WevView要显示的网页
        mWebView.loadDataWithBaseURL(null, mHtml, "text/html", "utf-8",
                null);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        mWebView.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。

        // 点击链接由自己处理，而不是新开Android的系统browser响应该链接。
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                //设置点击网页里面的链接还是在当前的webview里跳转
                view.loadUrl(url);
                return true;
            }
        });
    }
}

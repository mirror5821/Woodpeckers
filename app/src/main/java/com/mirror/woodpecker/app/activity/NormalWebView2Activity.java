package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;

import com.mirror.woodpecker.app.R;

import dev.mirror.library.android.view.ProgressWebView;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class NormalWebView2Activity extends  BaseActivity{

    private ProgressWebView webview;
    private String url;
    private String mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);
        setTitleText("");
        setBack();

        mIntent = getIntent().getStringExtra(INTENT_ID);

        url = mIntent;

        //绑定控件
        webview = (ProgressWebView) findViewById(R.id.webview);

        //设置数据

        //http://zmnyw.cn/index.php?s=/Home/Article/detail/id/60.html
        //\/index.php?s=\/Home\/Article\/detail\/id\/27.html
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        webview.loadUrl(url);
    }
}

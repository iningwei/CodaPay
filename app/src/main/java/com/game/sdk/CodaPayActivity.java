package com.game.sdk;

import androidx.annotation.RequiresApi;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CodaPayActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getId(this,"layout","activity_codapay"));
        String url=getIntent().getStringExtra("URL");
        Log.e("UnityPlugins","url:"+url);

        webView=(WebView)this.findViewById(getId(this,"id","CodaPayWebView"));
        WebSettings setting=webView.getSettings();
        setting.setJavaScriptEnabled(true);
        //设置屏幕自适应
        setting.setUseWideViewPort(true); //将图片调整到适合webview的大小
        setting.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        setting.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        setting.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        setting.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //配置WebViewClient
        WebViewClient mWebviewclient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //return super.shouldOverrideUrlLoading(view, url);
                //返回true代表在当前webview中打开，返回false表示打开浏览器
                if (url.startsWith("http:")||url.startsWith("https:")||url.startsWith("ftp")){
                    view.loadUrl(url);
                    return true;
                }
                else if (url.startsWith("scheme://")){
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        };
        webView.setWebViewClient(mWebviewclient);

        webView.loadUrl(url);
    }



    static int getId(Context paramContext, String defType, String resName){
        return paramContext.getResources().getIdentifier(resName,defType,paramContext.getPackageName());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.e("UnityPlugins", "onCodaPayActivity Destroy");
        CodaPayCenter.helper.fireFinishListener();
    }
}
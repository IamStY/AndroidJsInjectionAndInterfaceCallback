package com.example.stevenyang.webviewinjection;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    WebView wv;
    CountDownTimer countDownTimer = new CountDownTimer(5000,1000) {
        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
        Toast.makeText(getApplicationContext(),"倒數結束",Toast.LENGTH_LONG).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wv = (WebView) this.findViewById(R.id.wv);
        wv.setVisibility(View.INVISIBLE);
        wv.getSettings().setJavaScriptEnabled(true);

        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });
        wv.addJavascriptInterface(new WebAppInterface(this), "Android");

        wv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                wv.loadUrl(
                        "javascript:(function() { " +
                                "document.getElementsByClassName(\"_15ko\")[0].addEventListener(\"click\", function(){if(document.getElementsByClassName(\"_15ko\")[0]." +
                                "getAttribute('aria-pressed')==\"true\"){Android.FunctionName('文章點讚');}else{Android.FunctionName('取消文章點讚');}})" +
                                "})()");
                wv.setVisibility(View.VISIBLE);
                countDownTimer.start();

                Log.e("webview url", wv.getUrl());
                super.onPageFinished(view, url);
            }

//if(document.getElementsByClassName("_15ko")[0].getAttribute('aria-pressed')=="true"){alert('hahaha')}else{alert('fuckyou')}
        });


        wv.loadUrl("https://www.facebook.com/CocowallApp/photos/a.1118799288162903.1073741828.1108649969177835/1347204571989039/?type=3&theater");
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void FunctionName(String value) {
            Toast.makeText(mContext,value,Toast.LENGTH_SHORT).show();
        }
    }
    private void injectCSS() {
        try {

            wv.loadUrl( "javascript:(function() { " +"alert('fuck')"+
                        "})()");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//script.text = "document.getElementsByClassName("_khz")[0].addEventListener("click", function(){alert("CCC");});"
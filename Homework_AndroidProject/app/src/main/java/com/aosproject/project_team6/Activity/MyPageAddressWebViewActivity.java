package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.R;

public class MyPageAddressWebViewActivity extends AppCompatActivity {

    WebView webView;
    String macIP;
    Handler handler;

    class MyJavaScriptInterface
    {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processDATA(String data) {
            Bundle extra = new Bundle();
            Intent intent = new Intent();
            extra.putString("data", data);
            intent.putExtras(extra);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_address_web_view);

        macIP = ShareVar.IPAddress;
        webView = findViewById(R.id.webview_mypage_Address);

        handler = new Handler();

        //Web Setting
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); // JavaScript 사용 여부
        webSettings.setBuiltInZoomControls(true); // 확대 축소 사용 여부
        webSettings.setDisplayZoomControls(false); // 돋보기 사용 여부
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        webView.setWebChromeClient(new WebChromeClient()); // web client 를 chrome 으로 설정



        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {

                webView.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        webView.loadUrl("http://" + macIP + ":8080/assets/daum.html");

    }//onCreate
}
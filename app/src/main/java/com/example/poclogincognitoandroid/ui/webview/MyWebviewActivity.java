package com.example.poclogincognitoandroid.ui.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.poclogincognitoandroid.R;
import com.google.android.material.appbar.AppBarLayout;

public class MyWebviewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Same app");
        setContentView(R.layout.activity_webview);
        webView = findViewById(R.id.web);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://poc-iupp-sample-app.dev.iupp.io.s3-website-sa-east-1.amazonaws.com/#/");
    }
}

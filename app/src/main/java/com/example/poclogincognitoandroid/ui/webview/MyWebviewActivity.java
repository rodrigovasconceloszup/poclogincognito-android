package com.example.poclogincognitoandroid.ui.webview;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.poclogincognitoandroid.R;

public class MyWebviewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final String urlRedirect = getIntent().getStringExtra("urlRedirect");
        final String points = getIntent().getStringExtra("points");

        System.out.println(points);

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

        final String accessToken = urlRedirect.split("access_token")[1];
        final String idToken = urlRedirect.split("id_token")[1];
        webView.loadUrl("http://poc-iupp-sample-app.dev.iupp.io.s3-website-sa-east-1.amazonaws.com/#/code=" + accessToken);
    }
}

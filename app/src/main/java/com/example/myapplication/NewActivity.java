package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewActivity extends AppCompatActivity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        webView = findViewById(R.id.wv);

        Intent intent = getIntent();
        String link = intent.getStringExtra("LinkTinTuc");

        webView.loadUrl(link);

        webView.setWebViewClient(new WebViewClient());
    }
}

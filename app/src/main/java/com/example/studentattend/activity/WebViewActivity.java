package com.example.studentattend.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.studentattend.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        final String url = bundle.getString("url");
        webView = findViewById(R.id.web_view);
        status();
        webView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.finish_web_view) {
            finish();
        }
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void status() {
        //设置WebView的一些缩放功能点
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setSupportZoom(true);
        //设置WebView可触摸放大缩小
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setInitialScale(70);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        webView.getSettings().setUseWideViewPort(true);
        //允许JS执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        //使用缓存
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //取消滚动条白边效果
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();//返回上个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);//退出H5界面
    }
}
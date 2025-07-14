
package com.example.watchbrowser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.DownloadListener;
import androidx.wear.activity.WearableActivity;

public class MainActivity extends WearableActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com");

        // 下载监听器实现
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, 
                String contentDisposition, String mimetype, long contentLength) {
                // 处理图片/视频/APK下载
                new DownloadTask(MainActivity.this).execute(url, mimetype);
            }
        });
    }

    // 返回键控制
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            new AlertDialog.Builder(this)
                .setTitle("退出确认")
                .setMessage("确定要退出浏览器吗？")
                .setPositiveButton("确定", (dialog, which) -> finish())
                .setNegativeButton("取消", null)
                .show();
        }
    }
}

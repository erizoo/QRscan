package by.erizo.scan.qrscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import by.erizo.scan.WebViewController;

public class WebViewActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewlayout);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.help_webview);

        WebView webView;
        setContentView(R.layout.webviewlayout);
        webView = findViewById(R.id.help_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

    }
}

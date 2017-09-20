package com.wikidemo.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.wikidemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by winnerkm on 19/09/17.
 */

public class WebViewActivity extends Activity {
    private String url;
    private ProgressDialog mProgress;

    @Bind(R.id.faq_webView)
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent().getExtras() != null) {
            url = getIntent().getStringExtra("url");
        }
        setContentView(R.layout.web_view_activity);
        ButterKnife.bind(this);

        loadWebView(url);
    }

    private void loadWebView(String url) {
        webView.loadUrl(url);

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(WebViewActivity.this);
                    mProgress.show();
                }
                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView viewx, String urlx) {
                viewx.loadUrl(urlx);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(WebViewActivity.this,"Not a valid url please try again.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


}

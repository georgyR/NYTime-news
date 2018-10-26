package com.androidacademy.msk.exerciseproject.screen.news_details;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.androidacademy.msk.exerciseproject.R;
import com.androidacademy.msk.exerciseproject.screen.ViewVisibilitySwitcher;

import static com.androidacademy.msk.exerciseproject.screen.UiState.ERROR;
import static com.androidacademy.msk.exerciseproject.screen.UiState.HAS_DATA;
import static com.androidacademy.msk.exerciseproject.screen.UiState.LOADING;

public class NewsDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_URL = "EXTRA_URL";

    @NonNull
    private WebView webView;
    @NonNull
    private View errorView;
    @NonNull
    private Button tryGainButton;
    @NonNull
    private ProgressBar progressBar;
    @NonNull
    private ViewVisibilitySwitcher visibilitySwitcher;

    @NonNull
    public static Intent getStartIntent(@NonNull String url, @NonNull Context context) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        Toolbar toolbar = findViewById(R.id.all_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.activity_news_details__progressbar);
        errorView = findViewById(R.id.activity_news_details__view_error);
        webView = findViewById(R.id.activity_news_details__webview);
        setupWebView(webView, savedInstanceState);

        visibilitySwitcher = new ViewVisibilitySwitcher(webView, progressBar, errorView);

        tryGainButton = findViewById(R.id.view_error__button_try_again);
        tryGainButton.setOnClickListener(v -> webView.reload());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupWebView(@NonNull WebView webView,
                              @Nullable Bundle savedInstanceState) {
        webView.restoreState(savedInstanceState);
        webView.setWebViewClient(new DetailsWebViewClient());
        String url = getIntent().getStringExtra(EXTRA_URL);
        webView.loadUrl(url);
    }

    private class DetailsWebViewClient extends WebViewClient {

        private boolean loadingIsFailed;

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingIsFailed = false;
            visibilitySwitcher.setUiState(LOADING);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            loadingIsFailed = true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (loadingIsFailed) {
                visibilitySwitcher.setUiState(ERROR);
            } else {
                visibilitySwitcher.setUiState(HAS_DATA);
            }
        }
    }
}

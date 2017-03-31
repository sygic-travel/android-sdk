package com.sygic.travel.sdkdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdk.model.place.Reference.TITLE;
import static com.sygic.travel.sdk.model.place.Reference.URL;

public class ReferenceActivity extends AppCompatActivity {

	private WebView webView;
	private ProgressBar progressBar;

	private boolean loaded = false;
	private boolean touch = false;

	private List<String> loadedUrls = new ArrayList<>();
	private String referenceUrl;
	private String referenceTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reference);

		if(getIntent().getExtras() != null) {
			referenceUrl = getIntent().getExtras().getString(URL, "");
			referenceTitle = getIntent().getExtras().getString(TITLE, getString(R.string.title_activity_reference));
		}
		setTitle(referenceTitle);
		initWebView();
		loadReferenceUrl();
	}

	private void initWebView() {
		if (loaded) {
			return;
		}
		progressBar = (ProgressBar) findViewById(R.id.pb_reference);

		webView = (WebView) findViewById(R.id.wv_reference);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setSupportZoom(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setDisplayZoomControls(false);
		webView.setWebViewClient(getOnUrlOverrideClient());
		webView.setWebChromeClient(getOnConsoleMessageClient());
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setUseWideViewPort(true);

		webView.setOnTouchListener(getOnTouchListener());

		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
				if(progress < 100 && progressBar.getVisibility() == ProgressBar.GONE){
					progressBar.setVisibility(ProgressBar.VISIBLE);
				}

				progressBar.setProgress(progress);
				if(progress == 100) {
					progressBar.setVisibility(ProgressBar.GONE);
				}
			}
		});
	}

	private void loadReferenceUrl() {
		webView.loadUrl(referenceUrl);
		loaded = true;
		loadedUrls.add(referenceUrl);
	}

	public WebChromeClient getOnConsoleMessageClient(){
		return new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				ReferenceActivity.this.setProgress(progress * 1000);
			}
		};
	}

	private View.OnTouchListener getOnTouchListener() {
		return new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				touch = true;
				return false;
			}
		};
	}

	private WebViewClient getOnUrlOverrideClient() {
		return new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(touch){
					try {
						loadedUrls.add(url);
						webView.loadUrl(url);
						return true;
					} catch(Throwable throwable){
						ReferenceActivity.this.finish();
						return false;
					}
				} else {
					return false;
				}
			}
		};
	}
}

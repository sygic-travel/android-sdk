package itemDetail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.sygic.travel.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;

import itemDetail.toBeDeleted.Utils;

public class TripReferenceActivity extends AppCompatActivity {
	public static final String REFERENCE_DIR_PATH = "reference_dir_path";
	public static final String REFERENCE_URL = "referenceUrl";
	public static final String REFERENCE_TITLE = "referenceTitle";

	private WebView webView;
	private boolean loaded = false;
	private Menu menu;
	private boolean touch = false;
	private List<String> loadedUrls;
	private String referenceDirPath;
	private String url, title;
	private ProgressBar progressBar;
	private ActionBar supportActionBar;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);
		supportActionBar = Utils.setToolbar(this);

		supportActionBar.setDisplayHomeAsUpEnabled(true);

		if(getIntent().getExtras() != null) {
			referenceDirPath = getIntent().getStringExtra(REFERENCE_DIR_PATH);
			url = getIntent().getExtras().getString(REFERENCE_URL);
			title = getIntent().getExtras().getString(REFERENCE_TITLE);
			loadedUrls = new ArrayList<>();

			showContent();
		}
	}

	public void showContent() {
		String contentUrl;
		if(!Utils.isOnline(this)){
			if(referenceDirPath == null || referenceDirPath.equals("")){
				Toast.makeText(this, R.string.error_content_not_available_offline, Toast.LENGTH_LONG).show();
				finish();
				return;
			}
			contentUrl = "file://" + referenceDirPath;
		} else {
			contentUrl = url;
		}

		setActionBarTitle(title);
		setWebView(contentUrl);
	}
	
	private void setActionBarTitle(String name) {
		if(name != null && !name.equals("")){
			supportActionBar.setTitle(name);
		}
	}

	private void setWebView(String url) {
		if (loaded) {
			return;
		}
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);

		webView = (WebView) findViewById(R.id.about_tripomatic_web);
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
		webView.loadUrl(url);
		loaded = true;
		loadedUrls.add(url);
	}

	public WebChromeClient getOnConsoleMessageClient(){
		return new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				TripReferenceActivity.this.setProgress(progress * 1000);
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
//						startFallbackBrowser(url);
						loadedUrls.add(url);
						webView.loadUrl(url);
						checkUrlAndSetOpenBrowser(url);
						return true;
					} catch(Throwable throwable){
						Toast.makeText(
							TripReferenceActivity.this,
							R.string.error_dynamic_content,
							Toast.LENGTH_LONG
						).show();
						TripReferenceActivity.this.finish();
						return false;
					}
				} else {
					return false;
				}
			}
		};
	}

	private void checkUrlAndSetOpenBrowser(String url) {
		boolean visible = false;
		if(!url.startsWith("file:")){
			visible = true;
		}
		menu.findItem(R.id.action_dynamic_content_show_in_browser).setVisible(visible);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.dynamic_content, menu);
		menu.findItem(R.id.action_dynamic_content_call).setVisible(false);
		menu.findItem(R.id.action_dynamic_content_show_in_browser).setVisible(false);
		this.menu = menu;
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		int id = menuItem.getItemId();
		if(id == R.id.action_dynamic_content_show_in_browser){
			startFallbackBrowser(loadedUrls.get(loadedUrls.size() - 1));
		}
		return super.onOptionsItemSelected(menuItem);
	}

	private void startFallbackBrowser(String url) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			String extension = MimeTypeMap.getFileExtensionFromUrl(url);
			if(extension.equals("png") || extension.equals("jpg") || extension.equals("svg")){
				intent.setDataAndType(Uri.parse(url), "image/*");
			}else{
				intent.setData(Uri.parse(url));
			}
			startActivity(intent);
		} catch(Exception ignored) {
			Toast.makeText(this, R.string.error_no_default_browser, Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public Intent getSupportParentActivityIntent() {
		onBackPressed();
		return super.getSupportParentActivityIntent();
	}

	@Override
	public void onBackPressed() {
		if(loadedUrls.size() <= 1){
			super.onBackPressed();
		} else if(loadedUrls.size() > 1) {
			webView.loadUrl(loadedUrls.get(loadedUrls.size() - 2));
			loadedUrls.remove(loadedUrls.size() - 1);
		}
	}
}

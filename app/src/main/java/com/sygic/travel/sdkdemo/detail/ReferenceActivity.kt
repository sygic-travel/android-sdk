package com.sygic.travel.sdkdemo.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.sygic.travel.sdkdemo.R
import java.util.ArrayList

class ReferenceActivity : AppCompatActivity() {

	private var webView: WebView? = null
	private var progressBar: ProgressBar? = null

	private var loaded = false
	private var touch = false

	private val loadedUrls = ArrayList<String?>()
	private var referenceUrl: String? = null
	private var referenceTitle: String? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_reference)

		// Reference is basically only URL to be loaded in a web view
		if (intent.extras != null) {
			referenceUrl = intent.extras.getString(REFERENCE_URL, "")
			referenceTitle = intent.extras.getString(REFERENCE_TITLE, getString(R.string.title_activity_reference))
		}
		title = referenceTitle
		initWebView()
		loadReferenceUrl()
	}

	// Web view initialization
	private fun initWebView() {
		if (loaded) {
			return
		}
		progressBar = findViewById(R.id.pb_reference)

		webView = findViewById(R.id.wv_reference)
		webView!!.settings.javaScriptEnabled = true
		webView!!.settings.domStorageEnabled = true
		webView!!.settings.setSupportZoom(true)
		webView!!.settings.builtInZoomControls = true
		webView!!.settings.displayZoomControls = false
		webView!!.setWebViewClient(onUrlOverrideClient)
		webView!!.setWebChromeClient(onConsoleMessageClient)
		webView!!.settings.loadWithOverviewMode = true
		webView!!.settings.useWideViewPort = true

		webView!!.setOnTouchListener(onTouchListener)

		webView!!.setWebChromeClient(object : WebChromeClient() {
			override fun onProgressChanged(view: WebView, progress: Int) {
				if (progress < 100 && progressBar!!.visibility == ProgressBar.GONE) {
					progressBar!!.visibility = ProgressBar.VISIBLE
				}

				progressBar!!.progress = progress
				if (progress == 100) {
					progressBar!!.visibility = ProgressBar.GONE
				}
			}
		})
	}

	private fun loadReferenceUrl() {
		webView!!.loadUrl(referenceUrl)
		loaded = true
		loadedUrls.add(referenceUrl)
	}

	val onConsoleMessageClient: WebChromeClient
		get() = object : WebChromeClient() {
			override fun onProgressChanged(view: WebView, progress: Int) {
				this@ReferenceActivity.setProgress(progress * 1000)
			}
		}

	private val onTouchListener: View.OnTouchListener
		get() = View.OnTouchListener { _, _ ->
			touch = true
			false
		}

	// Since we support Android API versions 15 and newer, we use the deprecated shouldOverrideUrlLoading method
	private val onUrlOverrideClient: WebViewClient
		get() = object : WebViewClient() {
			override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
				if (touch) {
					try {
						loadedUrls.add(url)
						webView!!.loadUrl(url)
						return true
					} catch (throwable: Throwable) {
						this@ReferenceActivity.finish()
						return false
					}

				} else {
					return false
				}
			}
		}

	companion object {
		val REFERENCE_TITLE = "reference_title"
		val REFERENCE_URL = "reference_url"
	}
}

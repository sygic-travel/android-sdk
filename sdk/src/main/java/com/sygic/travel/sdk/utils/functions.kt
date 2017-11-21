package com.sygic.travel.sdk.utils

import android.os.Looper

fun checkNotRunningOnMainThread() {
	if (Looper.myLooper() == Looper.getMainLooper()) {
		throw IllegalStateException("Cannot access Sygic Travel SDK on the main thread since it may lock the UI for a long period of time.")
	}
}

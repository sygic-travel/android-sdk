package com.sygic.travel.sdk.utils

import android.os.Looper

internal fun checkNotRunningOnMainThread() {
	if (Looper.myLooper() == Looper.getMainLooper()) {
		throw IllegalStateException("Cannot access Sygic Travel SDK on the main thread since it may lock the UI for a long period of time.")
	}
}

internal fun checkUserDataSupport(userDataSupported: Boolean, module: String) {
	if (!userDataSupported) {
		throw IllegalStateException("$module module can be used only with enabled user-data support. To enable it, configure Sdk with clientId.")
	}
}

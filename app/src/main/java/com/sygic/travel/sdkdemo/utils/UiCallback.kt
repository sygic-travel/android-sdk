package com.sygic.travel.sdkdemo.utils

import android.app.Activity
import com.sygic.travel.sdk.Callback

@Suppress("MemberVisibilityCanPrivate")
open class UiCallback<in T>(private val activity: Activity) : Callback<T> {
	override fun onSuccess(data: T) {
		activity.runOnUiThread {
			if (!activity.isFinishing) {
				onUiSuccess(data)
			}
		}
	}

	override fun onFailure(exception: Throwable) {
		activity.runOnUiThread {
			if (!activity.isFinishing) {
				onUiFailure(exception)
			}
		}
	}

	open fun onUiSuccess(data: T) {
	}

	open fun onUiFailure(exception: Throwable) {
	}
}

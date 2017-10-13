package com.sygic.travel.sdk.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

internal object UserAgentUtil {
	fun createUserAgent(context: Context): String {
		var packageInfo: PackageInfo? = null
		try {
			packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
		} catch (e: PackageManager.NameNotFoundException) {
			e.printStackTrace()
		}

		return StringBuilder()
			.append(context.packageName).append("/")
			.append(if (packageInfo != null) packageInfo.versionName + " " else "")
			.append("sygic-travel-sdk-android/sdk-version").append(" ")
			.append("Android/").append(Build.VERSION.RELEASE).toString()
	}
}

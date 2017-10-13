package com.sygic.travel.sdkdemo.auth


import android.app.AlertDialog
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.sygic.travel.sdkdemo.R


class FacebookSignIn(
	private val activity: AuthActivity
) :
	View.OnClickListener,
	FacebookCallback<LoginResult> {

	internal val callbackManager = CallbackManager.Factory.create()
	private var requestingPermissions: Boolean = false

	override fun onClick(view: View) {
	}

	override fun onSuccess(loginResult: LoginResult) {
		if (loginResult.recentlyDeniedPermissions.contains(EMAIL)) {
			showEmailRequiredDialog()
		} else {
			activity.viewModel.signInFacebook(activity, loginResult.accessToken.token)
			LoginManager.getInstance().logOut()
		}
	}

	private fun showEmailRequiredDialog() {
		requestingPermissions = false
		val emailRequiredDialog = AlertDialog.Builder(activity)
		emailRequiredDialog.setCancelable(false)
		emailRequiredDialog.setTitle(R.string.fb_login_email_required_title)
		emailRequiredDialog.setMessage(R.string.fb_login_email_required_message)
		emailRequiredDialog.setPositiveButton(R.string.yes, { _, _ ->
			if (!requestingPermissions) {
				requestingPermissions = true
				LoginManager.getInstance().logInWithReadPermissions(
					activity,
					listOf(EMAIL)
				)
			}
		})
		emailRequiredDialog.setNegativeButton(R.string.no, { dialogInterface, _ ->
			dialogInterface.dismiss()
			onCancel()
		})
		emailRequiredDialog.show()
	}

	// cancelled by user, no error message
	override fun onCancel() {
		LoginManager.getInstance().logOut()
	}

	override fun onError(fbException: FacebookException) {
		if (fbException.message == FB_NO_INTERNET_EXCEPTION) {
			Toast.makeText(activity, "No internet connection", Toast.LENGTH_LONG).show()
			Log.e(TAG, "No internet connectivity.")
		} else {
			Toast.makeText(activity, "Unknown error occurred", Toast.LENGTH_LONG).show()
			Log.e(TAG, "Error: " + fbException.message)
		}
		LoginManager.getInstance().logOut()
	}

	fun onActivityResult(requestCode: Int, responseCode: Int, intent: Intent) {
		callbackManager.onActivityResult(requestCode, responseCode, intent)
	}

	companion object {
		val TAG = FacebookSignIn::class.java.simpleName
		const val FB_NO_INTERNET_EXCEPTION = "net::ERR_INTERNET_DISCONNECTED"
		const val EMAIL = "email"
	}
}

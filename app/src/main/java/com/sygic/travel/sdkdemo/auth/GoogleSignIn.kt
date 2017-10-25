package com.sygic.travel.sdkdemo.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R


class GoogleSignIn(
	private val activity: AuthActivity
) :
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener,
	View.OnClickListener {

	private var googleApiClient: GoogleApiClient? = null

	init {
		val sdk = (activity.application as Application).sdk
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestEmail()
			.requestProfile()
			.requestServerAuthCode(activity.getString(R.string.google_server_client_id), false)
			.build()
		googleApiClient = GoogleApiClient.Builder(activity)
			.enableAutoManage(activity, this)
			.addApi(Auth.GOOGLE_SIGN_IN_API, gso)
			.build()
	}

	override fun onConnectionFailed(connectionResult: ConnectionResult) {
		Log.d(TAG, "onConnectionFailed:" + connectionResult)
	}

	override fun onClick(v: View) {
		val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
		activity.startActivityForResult(signInIntent, RC_SIGN_IN)
	}

	fun onActivityResult(requestCode: Int, data: Intent) {
		if (requestCode == RC_SIGN_IN) {
			val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
			handleSignInResult(result)
		}
	}

	private fun handleSignInResult(result: GoogleSignInResult?) {
		if (result != null && result.isSuccess) {
			signInWithToken(result)
		} else {
			Log.e(TAG, "Google authentication failure. " + result?.status?.statusMessage)
		}
	}

	private fun signInWithToken(result: GoogleSignInResult) {
		val account = result.signInAccount
		if (account != null) {
			activity.viewModel.signInGoogle(activity, account.serverAuthCode!!)
		} else {
			Log.e(TAG, "Authentication failed")
		}
	}

	fun disconnectGoogleApiClient() {
		if (googleApiClient != null && googleApiClient!!.isConnected) {
			googleApiClient!!.stopAutoManage(activity)
			googleApiClient!!.disconnect()
		}
	}

	fun connectGoogleApiClient() {
		if (googleApiClient != null && !googleApiClient!!.isConnected) {
			googleApiClient!!.connect()
		}
	}

	override fun onConnected(bundle: Bundle?) {}

	override fun onConnectionSuspended(i: Int) {}

	companion object {
		private val TAG = GoogleSignIn::class.java.simpleName
		const val RC_SIGN_IN = 9009
		const val REQUEST_AUTHORIZATION = 9010
	}
}

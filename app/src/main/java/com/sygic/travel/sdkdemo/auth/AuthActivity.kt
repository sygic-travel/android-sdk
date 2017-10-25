package com.sygic.travel.sdkdemo.auth

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.facebook.login.DefaultAudience
import com.facebook.login.LoginBehavior
import com.facebook.login.widget.LoginButton
import com.google.android.gms.common.SignInButton
import com.sygic.travel.sdk.Sdk
import com.sygic.travel.sdkdemo.Application
import com.sygic.travel.sdkdemo.R


class AuthActivity : AppCompatActivity() {

	private lateinit var etUsername: EditText
	private lateinit var etPassword: EditText
	private lateinit var btnSignIn: Button
	private lateinit var btnResetPassword: Button
	private lateinit var btnSignInFacebook: LoginButton
	private lateinit var btnSignInGoogle: SignInButton
	private lateinit var btnRegister: Button
	private lateinit var btnLogout: Button
	private lateinit var btnJwtSignIn: Button
	private lateinit var btnAnonymous: Button
	private lateinit var tvLoggedIn: TextView
	private lateinit var googleSignIn: GoogleSignIn
	private lateinit var facebookSignIn: FacebookSignIn
	lateinit var viewModel: AuthViewModel
	lateinit var sdk: Sdk

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		sdk = (application as Application).sdk
		setContentView(R.layout.activity_auth)
		viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)
		initViews()
	}

	private fun initViews() {
		etUsername = findViewById(R.id.et_username)
		etPassword = findViewById(R.id.et_password)
		btnSignIn = findViewById(R.id.btn_sign_in)
		btnResetPassword = findViewById(R.id.btn_reset_password)
		btnSignInGoogle = findViewById(R.id.btn_sign_in_google)
		btnSignInFacebook = findViewById(R.id.btn_sign_in_facebook)
		btnRegister = findViewById(R.id.btn_sign_up)
		btnLogout = findViewById(R.id.btn_logout)
		tvLoggedIn = findViewById(R.id.tv_logged_in)
		btnJwtSignIn = findViewById(R.id.btn_sign_in_jwt)
		btnAnonymous = findViewById(R.id.btn_sign_in_anonymous)

		btnSignIn.setOnClickListener { signInPassword() }
		btnResetPassword.setOnClickListener { resetPassword() }
		btnRegister.setOnClickListener { signUp() }
		btnLogout.setOnClickListener { logout() }
		btnJwtSignIn.setOnClickListener { jwtSignIn() }
		btnAnonymous.setOnClickListener { signUpAsAnonymous() }

		viewModel.refreshLoginStatus(this)
		initFacebookSignIn()
		initGoogleSignIn()
	}

	private fun jwtSignIn() {
		viewModel.jwtTokenSignIn(this)
	}

	private fun signUpAsAnonymous() {
		viewModel.anonymousSignIn(this)
	}

	private fun logout() {
		viewModel.logout(this)
	}

	private fun initFacebookSignIn() {
		facebookSignIn = FacebookSignIn(this)
		btnSignInFacebook.setOnClickListener(facebookSignIn)
		btnSignInFacebook.setReadPermissions(FacebookSignIn.EMAIL)
		btnSignInFacebook.registerCallback(facebookSignIn.callbackManager, facebookSignIn)
		btnSignInFacebook.defaultAudience = DefaultAudience.FRIENDS
		btnSignInFacebook.loginBehavior = LoginBehavior.NATIVE_WITH_FALLBACK
	}

	private fun initGoogleSignIn() {
		googleSignIn = GoogleSignIn(this)
		btnSignInGoogle.setSize(com.google.android.gms.common.SignInButton.SIZE_WIDE)
		btnSignInGoogle.setOnClickListener(googleSignIn)
	}

	override fun onActivityResult(requestCode: Int, responseCode: Int, intent: Intent) {
		if (requestCode == GoogleSignIn.RC_SIGN_IN || requestCode == GoogleSignIn.REQUEST_AUTHORIZATION) {
			googleSignIn.onActivityResult(requestCode, intent)
		} else if (requestCode == REQUEST_SIGN_IN_UP && responseCode == RESULT_SIGNED_CONTINUE) {
			finish()
		} else {
			facebookSignIn.onActivityResult(requestCode, responseCode, intent)
		}
	}

	internal fun setLoggedIn() {
		tvLoggedIn.text = "You are logged in"
	}

	internal fun setLoggedOut() {
		tvLoggedIn.text = "You are not logged in"
	}

	private fun signUp() {
		val signUpIntent = Intent(this, SignUpActivity::class.java)
		startActivity(signUpIntent)
	}

	private fun signInPassword() {
		val userName = etUsername.text.toString()
		val passwd = etPassword.text.toString()

		if (isInputValid(userName, passwd)) {
			viewModel.signInPassword(activity = this, name = userName, password = passwd)
		}
	}

	private fun resetPassword() {
		val userName = etUsername.text.toString()
		viewModel.resetPassword(this, userName)
	}

	private fun isInputValid(userName: String, passwd: String): Boolean {
		return if (userName.isNotBlank() && passwd.isNotBlank()) {
			val matcher = Patterns.EMAIL_ADDRESS.matcher(userName)
			if (matcher.matches()) {
				true
			} else {
				Toast.makeText(this, "Not a valid Email address!",
					Toast.LENGTH_LONG).show()
				false
			}
		} else {
			Toast.makeText(this, "Both Email and Password must be filled!",
				Toast.LENGTH_LONG).show()
			false
		}
	}

	override fun onStop() {
		super.onStop()
		googleSignIn.disconnectGoogleApiClient()
	}

	override fun onStart() {
		super.onStart()
		googleSignIn.connectGoogleApiClient()
	}

	companion object {
		const val REQUEST_SIGN_IN_UP = 332
		const val RESULT_SIGNED_CONTINUE = 333
	}
}

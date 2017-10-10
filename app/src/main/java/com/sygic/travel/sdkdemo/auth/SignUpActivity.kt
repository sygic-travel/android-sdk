package com.sygic.travel.sdkdemo.auth

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.sygic.travel.sdkdemo.R


class SignUpActivity : AppCompatActivity() {

	private lateinit var etEmail: EditText
	private lateinit var etName: EditText
	private lateinit var etPassword1: EditText
	private lateinit var etPassword2: EditText
	private lateinit var btnSignUp: Button
	private lateinit var viewModel: AuthViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_sign_up)
		viewModel = ViewModelProviders.of(this).get(AuthViewModel::class.java)

		initViews()
	}

	private fun initViews() {
		etEmail = findViewById(R.id.et_reg_username)
		etName = findViewById(R.id.et_reg_name)
		etPassword1 = findViewById(R.id.et_reg_password1)
		etPassword2 = findViewById(R.id.et_reg_password2)
		btnSignUp = findViewById(R.id.btn_reg_sign_up)

		btnSignUp.setOnClickListener { signUp() }
	}

	private fun signUp() {
		val email = etEmail.text.toString()
		val name = etName.text.toString()
		val passwd1 = etPassword1.text.toString()
		val passwd2 = etPassword2.text.toString()

		if (isInputValid(email, passwd1, passwd2)) {
			viewModel.signUpUser(activity = this, name = name, email = email, password = passwd1)
		}
	}

	private fun isInputValid(email: String, passwd1: String, passwd2: String): Boolean {
		if (email.isNotBlank() && passwd1.isNotBlank() && passwd2.isNotBlank()) {
			if (passwd1 == passwd2 && passwd1.length >= 8) {
				val matcher = Patterns.EMAIL_ADDRESS.matcher(email)
				return if (matcher.matches()) {
					true
				} else {
					Toast.makeText(this,
						"Email address is not valid !",
						Toast.LENGTH_LONG).show()
					false
				}
			} else {
				Toast.makeText(this,
					"Passwords don't match, or they are shorter than 8 characters.",
					Toast.LENGTH_LONG).show()
				etPassword1.setText("")
				etPassword2.setText("")

				return false
			}
		} else {
			Toast.makeText(this, "Only Name field is optional, all other must be filled!",
				Toast.LENGTH_LONG).show()
			return false
		}
	}
}

package com.sygic.travel.sdk.auth.model

import com.google.gson.annotations.SerializedName

data class UserRegistrationResponse(
	@SerializedName("id")
	val id: String
)

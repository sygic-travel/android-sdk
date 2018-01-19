package com.sygic.travel.sdk.auth.model

import com.google.gson.annotations.SerializedName

internal data class UserRegistrationResponse(
	@SerializedName("id")
	val id: String
)

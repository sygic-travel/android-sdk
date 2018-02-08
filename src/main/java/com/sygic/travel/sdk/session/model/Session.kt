package com.sygic.travel.sdk.session.model

import java.util.Date

data class Session(
	val accessToken: String,
	val expiresAt: Date
)

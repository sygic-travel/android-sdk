package com.sygic.travel.sdk.session.model

import org.threeten.bp.Instant

data class Session constructor(
	val accessToken: String,
	val expiresAt: Instant
)

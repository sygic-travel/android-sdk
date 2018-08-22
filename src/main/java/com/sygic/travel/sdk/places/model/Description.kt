package com.sygic.travel.sdk.places.model

@Suppress("unused")
class Description constructor(
	val text: String,
	val provider: String?,
	val providerLink: String?,
	val isTranslated: Boolean,
	val translationProvider: String?
)

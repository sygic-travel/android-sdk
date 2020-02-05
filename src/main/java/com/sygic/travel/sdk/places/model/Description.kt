package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.common.Language

@Suppress("unused")
class Description constructor(
	val text: String,
	val provider: String?,
	val providerLink: String?,
	val translationProvider: String?,
	val language: Language?
)

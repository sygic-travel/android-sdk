package com.sygic.travel.sdk.places.model

@Suppress("unused")
class Description constructor(
	val text: String,
	val provider: DescriptionProvider,
	val isTranslated: Boolean,
	val translationProvider: TranslationProvider,
	val link: String?
)

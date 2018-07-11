package com.sygic.travel.sdk.places.model

@Suppress("unused")
class Description(
	val text: String,
	val provider: DescriptionProvider,
	val translationProvider: TranslationProvider,
	val link: String?
)

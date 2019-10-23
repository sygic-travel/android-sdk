package com.sygic.travel.sdk.trips.model

data class TripMedia constructor(
	val squareMediaId: String,
	val squareUrlTemplate: String,
	val landscapeMediaId: String,
	val landscapeUrlTemplate: String,
	val portraitId: String,
	val portraitUrlTemplate: String,
	val videoPreviewId: String?,
	val videoPreviewUrlTemplate: String?
)

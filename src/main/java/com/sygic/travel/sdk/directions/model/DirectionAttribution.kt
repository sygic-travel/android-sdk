package com.sygic.travel.sdk.directions.model

import java.io.Serializable

@Suppress("unused")
class DirectionAttribution(
	val name: String,
	val url: String?,
	val logoUrl: String?,
	val license: String?
) : Serializable

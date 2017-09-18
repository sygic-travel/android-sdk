package com.sygic.travel.sdk

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

object Parser {
	private val gson = GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.create()

	fun <PT> parseJson(json: String, typeToken: Type): PT {
		return gson.fromJson(json, typeToken)
	}
}

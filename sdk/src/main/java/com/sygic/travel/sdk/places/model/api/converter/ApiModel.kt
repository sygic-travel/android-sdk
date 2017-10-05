package com.sygic.travel.sdk.places.model.api.converter

internal interface ApiModel<out R> {
	fun convert(): R
}

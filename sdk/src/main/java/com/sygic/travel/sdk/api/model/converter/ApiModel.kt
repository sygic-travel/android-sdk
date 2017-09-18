package com.sygic.travel.sdk.api.model.converter

internal interface ApiModel<out R> {
	fun convert(): R
}

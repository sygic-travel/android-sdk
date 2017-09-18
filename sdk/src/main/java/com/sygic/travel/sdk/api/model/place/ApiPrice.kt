package com.sygic.travel.sdk.api.model.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.model.place.Price

internal class ApiPrice : ApiModel<Price> {

	@SerializedName("value")
	var value: Float = 0.toFloat()

	@SerializedName("savings")
	var savings: Float = 0.toFloat()

	override fun convert(): Price {
		val price = Price()

		price.value = value
		price.savings = savings

		return price
	}
}

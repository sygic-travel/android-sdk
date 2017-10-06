package com.sygic.travel.sdk.places.model.api.place

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.places.model.Price
import com.sygic.travel.sdk.places.model.api.converter.ApiModel

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

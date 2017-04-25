package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;

public class Price {
	public static final String VALUE = "value";
	public static final String SAVINGS = "savings";

	@SerializedName(VALUE)
	private float value;

	@SerializedName(SAVINGS)
	private float savings;

	public Price() {
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public float getSavings() {
		return savings;
	}

	public void setSavings(float savings) {
		this.savings = savings;
	}
}

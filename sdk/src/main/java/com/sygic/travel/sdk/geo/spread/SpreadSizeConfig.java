package com.sygic.travel.sdk.geo.spread;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class SpreadSizeConfig {
	private int radius;
	private int margin;
	private String name;
	private boolean photoRequired;
	private float minimalRating;

	public SpreadSizeConfig(
		int radius,
		int margin,
		String name,
		boolean photoRequired,
		float minimalRating
	) {
		this.radius = radius;
		this.margin = margin;
		this.name = name;
		this.photoRequired = photoRequired;
		this.minimalRating = minimalRating;
	}

	public int getRadius() {
		return radius;
	}

	public int getMargin() {
		return margin;
	}

	public String getName() {
		return name;
	}

	public boolean isPhotoRequired() {
		return photoRequired;
	}

	public float getMinimalRating() {
		return minimalRating;
	}
}

package com.sygic.travel.sdk.geo.spread;

/**
 * <p>Place's configuration values for spreading.</p>
 */
public class SpreadSizeConfig {
	public static final String SMALL = "small";
	public static final String MEDIUM = "medium";
	public static final String BIG = "big";
	public static final String POPULAR = "popular";
	public static final String INVISIBLE = "invisible";

	private int radius;
	private int margin;
	private String name;
	private boolean photoRequired;
	private float minimalRating;

	/**
	 * <p>Place's configuration values for spreading.</p>
	 * @param radius Circular marker's radius.
	 * @param margin Marker's minimal margin.
	 * @param name String representation of size. One of following values:
	 * <ul>
	 *     <li>{@link SpreadSizeConfig#SMALL}</li>
	 *     <li>{@link SpreadSizeConfig#MEDIUM}</li>
	 *     <li>{@link SpreadSizeConfig#BIG}</li>
	 *     <li>{@link SpreadSizeConfig#POPULAR}</li>
	 * </ul>
	 * @param photoRequired Flag whether marker photo is required.
	 * @param minimalRating Minimal rating to show place's marker.
	 */
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

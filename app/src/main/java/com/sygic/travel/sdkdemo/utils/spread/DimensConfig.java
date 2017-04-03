package com.sygic.travel.sdkdemo.utils.spread;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class DimensConfig {
	private int popularMargin, bigMargin, mediumMargin, smallMargin;
	private int popularSize, bigSize, mediumSize, smallSize;

	public DimensConfig(){}

	public DimensConfig(int popularMargin, int bigMargin, int mediumMargin, int smallMargin, int popularSize, int bigSize, int mediumSize, int smallSize) {
		this.popularMargin = popularMargin;
		this.bigMargin = bigMargin;
		this.mediumMargin = mediumMargin;
		this.smallMargin = smallMargin;
		this.popularSize = popularSize;
		this.bigSize = bigSize;
		this.mediumSize = mediumSize;
		this.smallSize = smallSize;
	}

	public int getPopularMargin() {
		return popularMargin;
	}

	public int getBigMargin() {
		return bigMargin;
	}

	public int getMediumMargin() {
		return mediumMargin;
	}

	public int getSmallMargin() {
		return smallMargin;
	}

	public int getPopularSize() {
		return popularSize;
	}

	public int getBigSize() {
		return bigSize;
	}

	public int getMediumSize() {
		return mediumSize;
	}

	public int getSmallSize() {
		return smallSize;
	}
}

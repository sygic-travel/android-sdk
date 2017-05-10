package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;

/**
 * Medium's suitable usages.
 */
class Usage {
	private static final String SQUARE = "square";
	private static final String PORTRAIT = "portrait";
	private static final String LANDSCAPE = "landscape";
	private static final String VIDEO_PREVIEW = "video_preview";

	@SerializedName(SQUARE)
	private String square;

	@SerializedName(LANDSCAPE)
	private String landscape;

	@SerializedName(PORTRAIT)
	private String portrait;

	@SerializedName(VIDEO_PREVIEW)
	private String videoPreview;

	public String getSquare() {
		return square;
	}

	public String getLandscape() {
		return landscape;
	}

	public String getPortrait() {
		return portrait;
	}

	public String getVideoPreview() {
		return videoPreview;
	}
}

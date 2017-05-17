package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * <p>Place main media.</p>
 */
public class MainMedia {
	private static final String USAGE = "usage";
	private static final String MEDIA = "media";

	@SerializedName(USAGE)
	private Usage usage;
	@SerializedName(MEDIA)
	private List<Medium> media;

	public Usage getUsage() {
		return usage;
	}

	public List<Medium> getMedia() {
		return media;
	}
}

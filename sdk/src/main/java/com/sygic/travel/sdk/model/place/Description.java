package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Place description data.</p>
 */
public class Description {
	private static final String TEXT = "text";
	private static final String PROVIDER = "provider";
	private static final String TRANSLATION_PROVIDER = "translation_provider";
	private static final String URL = "url";

	@SerializedName(TEXT)
	private String text;

	@SerializedName(PROVIDER)
	private String provider;

	@SerializedName(TRANSLATION_PROVIDER)
	private String translationProvider;

	@SerializedName(URL)
	private String url;

	public Description() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getTranslationProvider() {
		return translationProvider;
	}

	public void setTranslationProvider(String translationProvider) {
		this.translationProvider = translationProvider;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

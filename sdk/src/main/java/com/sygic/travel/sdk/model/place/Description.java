package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Place's description data.</p>
 */
public class Description {
	public static final String TEXT = "text";
	public static final String PROVIDER = "provider";
	public static final String TRANSLATION_PROVIDER = "translation_provider";
	public static final String URL = "url";

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

package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;

/**
 * <p>Medium's source.</p>
 */
public class Source {
	public static final String NAME = "name";
	public static final String EXTERNAL_ID = "external_id";
	public static final String PROVIDER = "provider";

	@SerializedName(NAME)
	private String name;

	@SerializedName(EXTERNAL_ID)
	private String external_id;

	@SerializedName(PROVIDER)
	private String provider;

	public Source() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExternal_id() {
		return external_id;
	}

	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}

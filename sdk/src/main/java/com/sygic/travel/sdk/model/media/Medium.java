package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.List;

/**
 * <p>Place medium.</p>
 */

public class Medium {
	private static final String ID = "id";
	private static final String TYPE = "type";
	private static final String URL_TEMPLATE = "url_template";
	private static final String URL = "url";
	private static final String ORIGINAL = "original";
	private static final String SUITABILITY = "suitability";
	private static final String CREATED_AT = "created_at";
	private static final String SOURCE = "source";
	private static final String CREATED_BY = "created_by";
	private static final String QUADKEY = "quadkey";
	private static final String ATTRIBUTION = "attribution";
	private static final String LOCATION = "location";

	@SerializedName(ID)
	private String id;

	@SerializedName(TYPE)
	private String type;

	@SerializedName(URL_TEMPLATE)
	private String urlTemplate;

	@SerializedName(URL)
	private String url;

	@SerializedName(ORIGINAL)
	private Original original;

	@SerializedName(SUITABILITY)
	private List<String> suitability = null;

	@SerializedName(CREATED_AT)
	private String createdAt;

	@SerializedName(SOURCE)
	private Source source;

	@SerializedName(CREATED_BY)
	private String createdBy;

	@SerializedName(QUADKEY)
	private String quadkey;

	@SerializedName(ATTRIBUTION)
	private Attribution attribution;

	@SerializedName(LOCATION)
	private Location location;

	public Medium() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrlTemplate() {
		return urlTemplate;
	}

	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}

	public List<String> getSuitability() {
		return suitability;
	}

	public void setSuitability(List<String> suitability) {
		this.suitability = suitability;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getQuadkey() {
		return quadkey;
	}

	public void setQuadkey(String quadkey) {
		this.quadkey = quadkey;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}

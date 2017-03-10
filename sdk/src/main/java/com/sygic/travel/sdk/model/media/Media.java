package com.sygic.travel.sdk.model.media;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.List;

/**
 * Created by michal.murin on 16.2.2017.
 */

public class Media {
	public static final String GUID = "guid";
	public static final String TYPE = "type";
	public static final String URL_TEMPLATE = "type";
	public static final String PREVIEW_URL_TEMPLATE = "type";
	public static final String PREVIEW_URL = "type";
	public static final String URL = "type";
	public static final String CREATED_AT = "type";
	public static final String CREATED_BY = "type";
	public static final String LAT = "type";
	public static final String LNG = "type";
	public static final String LOCATION = "type";
	public static final String ORIGINAL = "type";
	public static final String ATTRIBUTION = "type";
	public static final String SOURCE = "type";
	public static final String SUITABILITY = "type";

	@SerializedName(GUID)
	private String guid;

	@SerializedName(TYPE)
	private String type;

	@SerializedName(URL_TEMPLATE)
	private String urlTemplate;

	@SerializedName(PREVIEW_URL_TEMPLATE)
	private String previewUrlTemplate;

	@SerializedName(PREVIEW_URL)
	private String previewUrl;

	@SerializedName(URL)
	private String url;

	@SerializedName(CREATED_AT)
	private String createdAt;
	
	@SerializedName(CREATED_BY)
	private String createdBy;
	
	@SerializedName(LAT)
	private float lat;
	
	@SerializedName(LNG)
	private float lng;
	
	@SerializedName(LOCATION)
	private Location location;
	
	@SerializedName(ORIGINAL)
	private Original original;
	
	@SerializedName(ATTRIBUTION)
	private Attribution attribution;
	
	@SerializedName(SOURCE)
	private Source source;
	
	@SerializedName(SUITABILITY)
	private List<String> suitability = null;

	public Media() {
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
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

	public String getPreviewUrlTemplate() {
		return previewUrlTemplate;
	}

	public void setPreviewUrlTemplate(String previewUrlTemplate) {
		this.previewUrlTemplate = previewUrlTemplate;
	}

	public Object getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Object getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public Object getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}

	public Object getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Original getOriginal() {
		return original;
	}

	public void setOriginal(Original original) {
		this.original = original;
	}

	public Attribution getAttribution() {
		return attribution;
	}

	public void setAttribution(Attribution attribution) {
		this.attribution = attribution;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public List<String> getSuitability() {
		return suitability;
	}

	public void setSuitability(List<String> suitability) {
		this.suitability = suitability;
	}
}

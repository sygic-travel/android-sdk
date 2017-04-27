package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.List;

/**
 * <p>Place.</p>
 */
public class Place {
	private static final String GUID = "guid";
	private static final String RATING = "rating";
	private static final String LOCATION = "location";
	private static final String QUADKEY = "quadkey";
	private static final String NAME = "name";
	private static final String NAME_SUFFIX = "name_suffix";
	private static final String BOUNDING_BOX = "bounding_box";
	private static final String PEREX = "perex";
	private static final String URL = "url";
	private static final String THUMBNAIL_URL = "thumbnail_url";
	private static final String PRICE = "price";
	private static final String MARKER = "marker";
	private static final String CATEGORIES = "categories";
	private static final String PARENTS = "parentsGuids";

	@SerializedName(GUID)
	private String guid;

	@SerializedName(RATING)
	private float rating;

	@SerializedName(LOCATION)
	private Location location;

	@SerializedName(QUADKEY)
	private String quadkey;

	@SerializedName(NAME)
	private String name;

	@SerializedName(NAME_SUFFIX)
	private String nameSuffix;

	@SerializedName(BOUNDING_BOX)
	private BoundingBox boundingBox;

	@SerializedName(PEREX)
	private String perex;

	@SerializedName(URL)
	private String url;

	@SerializedName(THUMBNAIL_URL)
	private String thumbnailUrl;

	@SerializedName(PRICE)
	private Price price;

	@SerializedName(MARKER)
	private String marker;

	@SerializedName(CATEGORIES)
	private List<String> categories;

	@SerializedName(PARENTS)
	private List<String> parentsGuids;

	public Place(){
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public boolean hasLocation(){
		return (location != null && (location.getLat() != 0f || location.getLng() != 0f));
	}

	public String getQuadkey() {
		return quadkey;
	}

	public void setQuadkey(String quadkey) {
		this.quadkey = quadkey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameSuffix() {
		return nameSuffix;
	}

	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	public String getPerex() {
		return perex;
	}

	public void setPerex(String perex) {
		this.perex = perex;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String photoUrl) {
		this.thumbnailUrl = photoUrl;
	}

	public boolean hasThumbnailUrl() {
		return thumbnailUrl != null && !thumbnailUrl.equals("");
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public List<String> getParentsGuids() {
		return parentsGuids;
	}

	public void setParentsGuids(List<String> parentsGuids) {
		this.parentsGuids = parentsGuids;
	}
}

package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.List;

/**
 * <p>Place.</p>
 */
public class Place {
	private static final String ID = "id";
	private static final String LEVEL = "level";
	private static final String CATEGORIES = "categories";
	private static final String RATING = "rating";
	private static final String QUADKEY = "quadkey";
	private static final String LOCATION = "location";
	private static final String BOUNDING_BOX = "bounding_box";
	private static final String NAME = "name";
	private static final String NAME_SUFFIX = "name_suffix";
	private static final String PEREX = "perex";
	private static final String URL = "url";
	private static final String THUMBNAIL_URL = "thumbnail_url";
	private static final String MARKER = "marker";
	private static final String PRICE = "price";
	private static final String PARENT_IDS = "parent_ids";

	@SerializedName(ID)
	private String id;

	@SerializedName(LEVEL)
	private String level;

	@SerializedName(CATEGORIES)
	private List<String> categories;

	@SerializedName(RATING)
	private float rating;

	@SerializedName(QUADKEY)
	private String quadkey;

	@SerializedName(LOCATION)
	private Location location;

	@SerializedName(BOUNDING_BOX)
	private BoundingBox boundingBox;

	@SerializedName(NAME)
	private String name;

	@SerializedName(NAME_SUFFIX)
	private String nameSuffix;

	@SerializedName(PEREX)
	private String perex;

	@SerializedName(URL)
	private String url;

	@SerializedName(THUMBNAIL_URL)
	private String thumbnailUrl;

	@SerializedName(MARKER)
	private String marker;

	@SerializedName(PRICE)
	private Price price;

	@SerializedName(PARENT_IDS)
	private List<String> parentIds;

	public Place(){
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getQuadkey() {
		return quadkey;
	}

	public void setQuadkey(String quadkey) {
		this.quadkey = quadkey;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public BoundingBox getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
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

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public List<String> getParentIds() {
		return parentIds;
	}

	public void setParentIds(List<String> parentIds) {
		this.parentIds = parentIds;
	}
}

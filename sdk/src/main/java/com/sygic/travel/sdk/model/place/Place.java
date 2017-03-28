package com.sygic.travel.sdk.model.place;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.List;

/**
 * Created by michal.murin on 16.2.2017.
 */

/*
class Place {
	 guid: string
	 rating: float
	 location: Location
	 quadkey: string
	 name: string
	 nameSuffix: string
	 boundingBox: BoundingBox
	 perex: string
	 url: string
	 photoUrl: string
	 price: Price
	 marker: string
	 tier: int
	 categories: array<string>
	 parentsGuids: array<string>
	 detail: Detail
 }
 */

public class Place {
	public static final String GUID = "guid";
	public static final String RATING = "rating";
	public static final String LOCATION = "location";
	public static final String QUADKEY = "quadkey";
	public static final String NAME = "name";
	public static final String NAME_SUFFIX = "name_suffix";
	public static final String BOUNDING_BOX = "bounding_box";
	public static final String PEREX = "perex";
	public static final String URL = "url";
	public static final String PHOTO_URL = "photo_url";
	public static final String PRICE = "price";
	public static final String MARKER = "marker";
	public static final String TIER = "tier";
	public static final String CATEGORIES = "categories";
	public static final String PARENTS = "parentsGuids";
	public static final String DETAIL = "detail";

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

	@SerializedName(PHOTO_URL)
	private String photoUrl;

	@SerializedName(PRICE)
	private Price price;

	@SerializedName(MARKER)
	private String marker;

	@SerializedName(TIER)
	private int tier;

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

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
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

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
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

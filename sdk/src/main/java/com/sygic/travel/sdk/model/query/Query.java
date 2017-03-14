package com.sygic.travel.sdk.model.query;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 10.3.2017.
 */

/*
class Query {
	query,
	bounds,
	categories,
	tags,
	parent,
	quadkeys,
	mapSpread,
	limit
}
*/

public class Query {
	public static final String QUERY = "query";
	public static final String BOUNDS = "bounds";
	public static final String CATEGORIES = "categories";
	public static final String TAGS = "tags";
	public static final String PARENT = "parent";
	public static final String QUADKEYS = "quadkeys";
	public static final String MAP_SPREAD = "map_spread";
	public static final String MAP_TILE = "map_tile";
	public static final String LIMIT = "limit";
	public static final String LEVEL = "level";

	@SerializedName(QUERY)
	private String query;

	@SerializedName(BOUNDS)
	private String bounds;

	@SerializedName(CATEGORIES)
	private String categories;

	@SerializedName(TAGS)
	private String tags;

	@SerializedName(PARENT)
	private String parent;

	@SerializedName(QUADKEYS)
	private int quadkeys;

	@SerializedName(MAP_SPREAD)
	private int mapSpread;

	@SerializedName(MAP_TILE)
	private String mapTile;

	@SerializedName(LIMIT)
	private int limit;

	@SerializedName(LEVEL)
	private String level;

	public Query(){}

	public Query(String query, String bounds, String categories, String tags, String parent, int quadkeys, int mapSpread, String mapTile, String level, int limit) {
		this.query = query;
		this.bounds = bounds;
		this.categories = categories;
		this.tags = tags;
		this.parent = parent;
		this.quadkeys = quadkeys;
		this.mapSpread = mapSpread;
		this.mapTile = mapTile;
		this.limit = limit;
		this.level = level;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getBounds() {
		return bounds;
	}

	public void setBounds(String bounds) {
		this.bounds = bounds;
	}

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public int getQuadkeys() {
		return quadkeys;
	}

	public void setQuadkeys(int quadkeys) {
		this.quadkeys = quadkeys;
	}

	public int getMapSpread() {
		return mapSpread;
	}

	public void setMapSpread(int mapSpread) {
		this.mapSpread = mapSpread;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getMapTile() {
		return mapTile;
	}

	public void setMapTile(String mapTile) {
		this.mapTile = mapTile;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
}

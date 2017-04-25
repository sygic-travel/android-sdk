package com.sygic.travel.sdk.model.query;

import com.google.gson.annotations.SerializedName;

/**
 * Query contains values for an API request. For parameters explanations see
 * <a href="http://alpha-docs.sygictravelapi.com/0.1/#endpoint-get-places">API Documentation</a>
 */
public class Query {
	public static final String QUERY = "query";
	public static final String BOUNDS = "bounds";
	public static final String CATEGORIES = "categories";
	public static final String TAGS = "tags";
	public static final String PARENTS = "parents";
	public static final String MAP_SPREAD = "map_spread";
	public static final String MAP_TILES = "map_tiles";
	public static final String LIMIT = "limit";
	public static final String LEVELS = "levels";

	@SerializedName(QUERY)
	private String query;

	@SerializedName(BOUNDS)
	private String bounds;

	@SerializedName(CATEGORIES)
	private String categories;

	@SerializedName(TAGS)
	private String tags;

	@SerializedName(PARENTS)
	private String parents;

	@SerializedName(MAP_SPREAD)
	private Integer mapSpread;

	@SerializedName(MAP_TILES)
	private String mapTiles;

	@SerializedName(LIMIT)
	private Integer limit;

	@SerializedName(LEVELS)
	private String levels;

	public Query(){}

	/**
	 * Query contains values for an API request. For parameters explanations see
	 * <a href="http://alpha-docs.sygictravelapi.com/0.1/#endpoint-get-places">API Documentation</a>
	 */
	public Query(
		String query,
		String levels,
		String categories,
		String mapTiles,
		Integer mapSpread,
		String bounds,
		String tags,
		String parents,
		Integer limit
	) {
		this.query = query;
		this.levels = levels;
		this.categories = categories;
		this.mapTiles = mapTiles;
		this.mapSpread = mapSpread;
		this.bounds = bounds;
		this.tags = tags;
		this.parents = parents;
		this.limit = limit;
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

	public String getParents() {
		return parents;
	}

	public void setParents(String parent) {
		this.parents = parent;
	}

	public Integer getMapSpread() {
		return mapSpread;
	}

	public void setMapSpread(Integer mapSpread) {
		this.mapSpread = mapSpread;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getMapTiles() {
		return mapTiles;
	}

	public void setMapTiles(String mapTiles) {
		this.mapTiles = mapTiles;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}
}

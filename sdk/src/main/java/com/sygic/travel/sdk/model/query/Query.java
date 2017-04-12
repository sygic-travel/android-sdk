package com.sygic.travel.sdk.model.query;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 10.3.2017.
 */

public class Query {
	public static final String QUERY = "query";
	public static final String BOUNDS = "bounds";
	public static final String CATEGORIES = "categories";
	public static final String TAGS = "tags";
	public static final String PARENTS = "parents";
	public static final String MAP_SPREAD = "map_spread";
	public static final String MAP_TILE = "map_tile";
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

	@SerializedName(MAP_TILE)
	private String mapTile;

	@SerializedName(LIMIT)
	private Integer limit;

	@SerializedName(LEVELS)
	private String levels;

	public Query(){}

	public Query(
		String query,
		String bounds,
		String categories,
		String tags,
		String parents,
		Integer mapSpread,
		String mapTile,
		String levels,
		Integer limit
	) {
		this.query = query;
		this.bounds = bounds;
		this.categories = categories;
		this.tags = tags;
		this.parents = parents;
		this.mapSpread = mapSpread;
		this.mapTile = mapTile;
		this.limit = limit;
		this.levels = levels;
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

	public String getMapTile() {
		return mapTile;
	}

	public void setMapTile(String mapTile) {
		this.mapTile = mapTile;
	}

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}
}

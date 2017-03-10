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
	public static final String LIMIT = "limit";

	@SerializedName(QUERY)
	private int query;

	@SerializedName(BOUNDS)
	private int bounds;

	@SerializedName(CATEGORIES)
	private int categories;

	@SerializedName(TAGS)
	private int tags;

	@SerializedName(PARENT)
	private int parent;

	@SerializedName(QUADKEYS)
	private int quadkeys;

	@SerializedName(MAP_SPREAD)
	private int mapSpread;

	@SerializedName(LIMIT)
	private int limit;

	public Query(){}

	public Query(int query, int bounds, int categories, int tags, int parent, int quadkeys, int mapSpread, int limit) {
		this.query = query;
		this.bounds = bounds;
		this.categories = categories;
		this.tags = tags;
		this.parent = parent;
		this.quadkeys = quadkeys;
		this.mapSpread = mapSpread;
		this.limit = limit;
	}

	public int getQuery() {
		return query;
	}

	public void setQuery(int query) {
		this.query = query;
	}

	public int getBounds() {
		return bounds;
	}

	public void setBounds(int bounds) {
		this.bounds = bounds;
	}

	public int getCategories() {
		return categories;
	}

	public void setCategories(int categories) {
		this.categories = categories;
	}

	public int getTags() {
		return tags;
	}

	public void setTags(int tags) {
		this.tags = tags;
	}

	public int getParent() {
		return parent;
	}

	public void setParent(int parent) {
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
}

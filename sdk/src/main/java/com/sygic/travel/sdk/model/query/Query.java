package com.sygic.travel.sdk.model.query;

import android.text.TextUtils;

import com.sygic.travel.sdk.model.geo.Bounds;

import java.util.List;

/**
 * <p>Query contains values for an API request. </p>
 * @see <a href="http://docs.sygictravelapi.com/0.2/#endpoint-get-places-list">API Documentation</a>
 */
public class Query {
	private String query;
	private Bounds bounds;
	private List<String> categories;
	private Operator categoriesOperator = Operator.OR;
	private List<String> tags;
	private Operator tagsOperator = Operator.OR;
	private List<String> parents;
	private Operator parentsOperator  = Operator.OR;
	private Integer mapSpread;
	private List<String> mapTiles;
	private Integer limit;
	private List<String> levels;

	/**
	 * Enumaration of possible logical operators.
	 */
	public enum Operator {
		AND(","),
		OR("%7C");

		String operator;

		Operator(String operator) {
			this.operator = operator;
		}

		public String getOperator() {
			return operator;
		}
	}

	public Query(){}

	/**
	 * Query contains values for an API request.
	 * @see <a href="http://docs.sygictravelapi.com/0.2/#endpoint-get-places-list">API Documentation</a>
	 */
	public Query(
		String query,
		Bounds bounds,
		List<String> categories,
		List<String> tags,
		List<String> parents,
		Integer mapSpread,
		List<String> mapTiles,
		Integer limit,
		List<String> levels
	) {
		this.query = query;
		this.bounds = bounds;
		this.categories = categories;
		this.tags = tags;
		this.parents = parents;
		this.mapSpread = mapSpread;
		this.mapTiles = mapTiles;
		this.limit = limit;
		this.levels = levels;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Bounds getBounds() {
		return bounds;
	}

	public void setBounds(Bounds bounds) {
		this.bounds = bounds;
	}

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public Operator getCategoriesOperator() {
		return categoriesOperator;
	}

	public void setCategoriesOperator(Operator categoriesOperator) {
		this.categoriesOperator = categoriesOperator;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public Operator getTagsOperator() {
		return tagsOperator;
	}

	public void setTagsOperator(Operator tagsOperator) {
		this.tagsOperator = tagsOperator;
	}

	public List<String> getParents() {
		return parents;
	}

	public void setParents(List<String> parents) {
		this.parents = parents;
	}

	public Operator getParentsOperator() {
		return parentsOperator;
	}

	public void setParentsOperator(Operator parentsOperator) {
		this.parentsOperator = parentsOperator;
	}

	public Integer getMapSpread() {
		return mapSpread;
	}

	public void setMapSpread(Integer mapSpread) {
		this.mapSpread = mapSpread;
	}

	public List<String> getMapTiles() {
		return mapTiles;
	}

	public void setMapTiles(List<String> mapTiles) {
		this.mapTiles = mapTiles;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public List<String> getLevels() {
		return levels;
	}

	public void setLevels(List<String> levels) {
		this.levels = levels;
	}

	public String getLevelsQueryString() {
		if(levels == null || levels.isEmpty()){
			return null;
		} else {
			return TextUtils.join(Operator.OR.operator, levels.toArray());
		}
	}

	public String getMapTilesQueryString() {
		if(mapTiles == null || mapTiles.isEmpty()){
			return null;
		} else {
			return TextUtils.join(Operator.OR.operator, mapTiles.toArray());
		}
	}

	public String getCategoriesQueryString() {
		if(categories == null || categories.isEmpty()){
			return null;
		} else {
			return TextUtils.join(categoriesOperator.operator, categories.toArray());
		}
	}

	public String getTagsQueryString() {
		if(tags == null || tags.isEmpty()){
			return null;
		} else {
			return TextUtils.join(tagsOperator.operator, tags.toArray());
		}
	}

	public String getParentsQueryString() {
		if(parents == null || parents.isEmpty()){
			return null;
		} else {
			return TextUtils.join(parentsOperator.operator, parents.toArray());
		}
	}

	public String getBoundsQueryString() {
		return bounds == null ? null : bounds.toQueryString();
	}
}

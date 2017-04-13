package com.sygic.travel.sdk.geo.spread;

import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class SpreadResult {
	List<SpreadedPlace> visiblePlaces;
	List<Place> hiddenPlaces;

	public SpreadResult(List<SpreadedPlace> visiblePlaces, List<Place> hiddenPlaces) {
		this.visiblePlaces = visiblePlaces;
		this.hiddenPlaces = hiddenPlaces;
	}

	public List<SpreadedPlace> getVisiblePlaces() {
		return visiblePlaces;
	}

	public void setVisiblePlaces(List<SpreadedPlace> visiblePlaces) {
		this.visiblePlaces = visiblePlaces;
	}

	public List<Place> getHiddenPlaces() {
		return hiddenPlaces;
	}

	public void setHiddenPlaces(List<Place> hiddenPlaces) {
		this.hiddenPlaces = hiddenPlaces;
	}
}

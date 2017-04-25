package com.sygic.travel.sdk.geo.spread;

import com.sygic.travel.sdk.model.place.Place;

import java.util.LinkedList;

public class SpreadResult {
	LinkedList<SpreadedPlace> visiblePlaces;
	LinkedList<Place> hiddenPlaces;

	/**
	 * Outupt of the spread algorithm.
	 * @param visiblePlaces Places visible on map.
	 * @param hiddenPlaces Places, which are not visible.
	 */
	public SpreadResult(LinkedList<SpreadedPlace> visiblePlaces, LinkedList<Place> hiddenPlaces) {
		this.visiblePlaces = visiblePlaces;
		this.hiddenPlaces = hiddenPlaces;
	}

	public LinkedList<SpreadedPlace> getVisiblePlaces() {
		return visiblePlaces;
	}

	public void setVisiblePlaces(LinkedList<SpreadedPlace> visiblePlaces) {
		this.visiblePlaces = visiblePlaces;
	}

	public LinkedList<Place> getHiddenPlaces() {
		return hiddenPlaces;
	}

	public void setHiddenPlaces(LinkedList<Place> hiddenPlaces) {
		this.hiddenPlaces = hiddenPlaces;
	}
}

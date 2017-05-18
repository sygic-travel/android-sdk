package com.sygic.travel.sdk.geo.spread;

import com.sygic.travel.sdk.model.geo.Bounds;
import com.sygic.travel.sdk.model.place.Place;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>Outupt of the {@link Spreader#spread(List, List, Bounds, CanvasSize) spread algorithm}.</p>
 */
public class SpreadResult {
	LinkedList<SpreadedPlace> visiblePlaces;
	LinkedList<Place> hiddenPlaces;

	/**
	 * <p>Outupt of the spread algorithm.</p>
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

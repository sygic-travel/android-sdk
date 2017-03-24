package placeDetail;

import com.sygic.travel.sdk.model.place.Reference;

import java.util.Comparator;

public class ReferenceComparator implements Comparator<Reference> {


	@Override
	public int compare(Reference reference1, Reference reference2) {
		if(reference2.getType().equals(PlaceDetailReferenceUtils.TOUR_PLACE) && !reference1.getType().equals(PlaceDetailReferenceUtils.TOUR_PLACE)) {
			return 1;
		} else if(reference1.getType().equals(PlaceDetailReferenceUtils.TOUR_PLACE) && !reference2.getType().equals(PlaceDetailReferenceUtils.TOUR_PLACE)) {
			return -1;
		} else {
			if(reference2.getPriority() > reference1.getPriority()) {
				return 1;
			} else if(reference2.getPriority() < reference1.getPriority()) {
				return -1;
			} else return 0;
		}
	}
}

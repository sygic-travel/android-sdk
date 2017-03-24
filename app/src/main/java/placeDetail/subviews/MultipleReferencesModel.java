package placeDetail.subviews;


import com.sygic.travel.sdk.model.place.Reference;

import java.util.List;

/**
 * Represents packed references to be shown in ReferencesListActivity.
 * Attribute reference is always first item in list to be eventualy shown as CTA
 */
public class MultipleReferencesModel implements PlaceDetailSubviewModel {
	private Reference reference;
	private List<Reference> otherReferences;
	private final int TYPE;

	public MultipleReferencesModel(Reference reference){
		this.reference = reference;
		TYPE = PlaceDetailSubviewModel.REFERENCE;
	}

	public MultipleReferencesModel(List<Reference> otherReferences) {
		this.otherReferences = otherReferences;
		reference = otherReferences.get(0);
		TYPE = MULTIPLE_REFERENCES;
	}

	public Reference getReference() {
		return reference;
	}

	public void setReference(Reference reference) {
		this.reference = reference;
	}

	public List<Reference> getOtherReferences() {
		return otherReferences;
	}

	public void setOtherReferences(List<Reference> otherReferences) {
		this.otherReferences = otherReferences;
	}

	@Override
	public int getType() {
		return TYPE;
	}
}
